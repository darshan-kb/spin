package com.spin.game.service;

import com.spin.game.config.beans.ResultQueue;
import com.spin.game.entities.*;
import com.spin.game.payloads.QueuePayload;
import com.spin.game.repository.BetCategoryRepository;
import com.spin.game.repository.BetValuesMapRepository;
import com.spin.game.repository.GameRepo;
import com.spin.game.serviceclass.ValueMap;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CalculateResultServiceImpl implements CalculateResultService{
    private GameRepo gameRepo;
    private BetValuesMapRepository betValuesMapRepo;
    private BetCategoryRepository betCategoryRepo;
    private Random random;
    private ValueMap valueMap;
    private StaticDBValuesService staticValues;
    private ResultQueue resultQueue;
    //private CountdownService countdownService;
    private final List<String> betNames = List.of("single","vSplit","row","hSplit","corner","zero","half","dozen","mis","column");
    @Override
    @Transactional
    public int getCurrentGameResult(long id) {

        List<Bet> allBets = gameRepo.findById(id).orElseThrow(() -> new IllegalStateException("game not available"))
                .getTickets()
                .stream()
                .flatMap((tickets -> tickets.getBets().stream()))
                .collect(Collectors.toList());

        int totalAmount = allBets.stream().map((bets) -> bets.getAmount()).reduce(0,(previousResult,element)->{
            return previousResult + element;
        });

        List<Integer> validElements = new ArrayList<>();

        for(int i=0;i<37;i++){
            double totalRewardOni=0.0;
            for(Bet b : allBets){
                List<Integer> elements = getElements(b);
                double payout = getXTimes(b.getBetName());
                int amount = b.getAmount();
                if(elements.contains(i)){
                    totalRewardOni += amount * payout;
                }
            }
            //System.out.println(totalRewardOni);
            if(totalRewardOni <= totalAmount*1.0){
                validElements.add(i);
            }
        }

//        Random random = new Random();
        int rand = random.nextInt(validElements.size());

        System.out.println(validElements);
        return validElements.get(rand);
    }
    @Override
    @Transactional
    public int getCurrentGameResultByValueMap(long id){
        List<Integer> validElements = new ArrayList<>();
        double totalAmount = gameRepo.findById(id).orElseThrow(() -> new IllegalStateException("Game id not found")).getTotalAmount();
        for(int i=0;i<37;i++){
            double totalRewardOni=0.0;

            for(String betName : betNames){
                int[] betValueList = valueMap.getBetList(betName);
                for(int j=0;j<betValueList.length;j++){
                    if(betValueList[j]==0)
                        continue;
                    if(j==0 && betName.equals("single"))
                        continue;
                    List<Integer> elements = staticValues.getElements(betName,j);
                    double payout = staticValues.getXTimes(betName);
                    int amount = betValueList[j];
                    if(elements.contains(i)){
                        totalRewardOni += amount * payout;
                    }
                }
            }
            //System.out.println(totalRewardOni);
            if(totalRewardOni <= totalAmount*1.0){
                validElements.add(i);
            }
        }
        int rand = random.nextInt(validElements.size());

        System.out.println(validElements);
        return validElements.get(rand);
    }


    public List<Integer> getElements(Bet b){
        String betValues = betValuesMapRepo.getElements(b.getBetName(), b.getValue()).orElseThrow(() -> new IllegalStateException("bet is not correct"));
        return Arrays.stream(betValues.split(",")).map((s) -> Integer.parseInt(s)).collect(Collectors.toList());
    }

    @Cacheable("betElementsCache")
    public List<Integer> getElements(String betName, int betIndex){
        System.out.println(betName+" "+betIndex);
        String betValues = betValuesMapRepo.getElements(betName, betIndex).orElseThrow(() -> new IllegalStateException("bet is not correct"));
        return Arrays.stream(betValues.split(",")).map((s) -> Integer.parseInt(s)).collect(Collectors.toList());
    }
    @Cacheable("betPayout")
    public double getXTimes(String betName){
        System.out.println("betName : "+betName);
        return betCategoryRepo.getBetCatgoryByBetName(betName).getBetPayout();
    }

    @Transactional
    public void markGameAsOver(Game game){
        game.setGameOver(true);
        gameRepo.save(game);
    }

    @Override
    public void updateQueue(Game game) {
        resultQueue.pop();
        resultQueue.push(new QueuePayload(game.getResultValue(),game.getGameTimeStamp()));
    }
}

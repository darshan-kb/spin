package com.spin.game.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.spin.game.config.beans.Countdown;
import com.spin.game.entities.*;
import com.spin.game.enums.GameName;
import com.spin.game.exception.DrawCloseException;
import com.spin.game.exception.UserNotFoundException;
import com.spin.game.model.TicketModel;
import com.spin.game.model.TicketRecordModel;
import com.spin.game.model.request.BetTransactionRequest;
import com.spin.game.model.response.GameResponse;
import com.spin.game.repository.BetRepository;
import com.spin.game.repository.GameRepo;
import com.spin.game.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService{

    private CountdownService countdownService;
    private TicketRepository ticketRepository;
    private BetService betService;
    private GameRepo gameRepo;
    private AccountDetailService accountDetailService;
    private Countdown countdown;
    private ValidationService validationService;

    @Override
    @Transactional
    public double saveTicket(String email, List<List<Integer>> boardValues) {
//        var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());

        if(countdown.getCountdown()<=10)
            throw new DrawCloseException();

        Game game = countdownService.getCurrentGame();
        double gameTotalAmt = game.getTotalAmount();
        int totalAmt = boardValues.stream().flatMap((bv)->bv.stream()).reduce(0, (previousresult, element)-> previousresult + element);
        totalAmt*=10;

        //try{
            Ticket t = ticketRepository.save(new Ticket(LocalDateTime.now(),totalAmt,game, email));
            betService.saveBet(boardValues,t);
            betService.addValueToValueMap(boardValues);
            game.setTotalAmount(gameTotalAmt+(totalAmt*1.0));
            gameRepo.save(game);
        //}
//        catch(RuntimeException e){
//            balance = accountDetailService.ticketError(totalAmt,email);
//            throw new RuntimeException();
//        }
        double balance;
        try{
            balance = accountDetailService.addTicket(totalAmt, t.getTicketId());
        }
        catch(Exception e){
            throw new RuntimeException("ticket is not created");
        }

        return balance;
    }

    @Override
    public GameResponse addTicket(BetTransactionRequest betTransactionRequest) {
        if(!validationService.validateBetTransactionRequest(betTransactionRequest)){
            return GameResponse.builder()
                    .gameId(String.valueOf(countdownService.getCurrentGame().getGameId()))
                    .externalId(betTransactionRequest.getTxnId())
                    .status("400")
                    .totalAmount(new BigDecimal(calculateTotalAmount(betTransactionRequest)))
                    .gameName(GameName.SPIN)
                    .message("Validation failed")
                    .build();
        }

        Game game = countdownService.getCurrentGame();
        List<List<Integer>> boardValues = jsonNodeToBetValues(betTransactionRequest);
        double gameTotalAmt = game.getTotalAmount();
        int totalAmt = boardValues.stream().flatMap((bv)->bv.stream()).reduce(0, (previousresult, element)-> previousresult + element);
        totalAmt*=10;

        Ticket t = ticketRepository.save(new Ticket(LocalDateTime.now(),totalAmt,game,betTransactionRequest.getUserDetails().getUsername()));
        betService.saveBet(boardValues,t);
        betService.addValueToValueMap(boardValues);
        game.setTotalAmount(gameTotalAmt+totalAmt);
        gameRepo.save(game);

        return GameResponse.builder()
                .gameId(String.valueOf(countdownService.getCurrentGame().getGameId()))
                .externalId(betTransactionRequest.getTxnId())
                .status("200")
                .totalAmount(new BigDecimal(totalAmt))
                .gameName(GameName.SPIN)
                .message("Bet successfully added")
                .build();
    }

    private List<List<Integer>> jsonNodeToBetValues(BetTransactionRequest betTransactionRequest){
        List<List<Integer>> betArrays = new ArrayList<>();
        for(JsonNode betValuesArray : betTransactionRequest.getBetDetails().getBetValues()){
            List<Integer> betArray = new ArrayList<>();
            for(JsonNode betValue : betValuesArray){
                betArray.add(betValue.asInt());
            }
            betArrays.add(betArray);
        }
        return betArrays;
    }

    @Override
    public Integer calculateTotalAmount(BetTransactionRequest betTransactionRequest) {
        int totalAmount=0;
        for(JsonNode betValuesArray : betTransactionRequest.getBetDetails().getBetValues()){
            for(JsonNode betValue : betValuesArray){
                totalAmount += betValue.asInt();
            }
        }
        return totalAmount;
    }


}

package com.spin.game.service;

import com.spin.game.dto.ClaimDTO;
import com.spin.game.entities.*;
import com.spin.game.mapper.Mapper;
import com.spin.game.payloads.AccountRedeemClaimPayload;
import com.spin.game.repository.ClaimBetRepository;
import com.spin.game.repository.GameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class ClaimService {
    @Autowired
    private ClaimBetRepository claimBetRepository;
    @Autowired
    private GameRepo gameRepo;
    @Autowired
    private CalculateResultService calculateResultService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private HttpRequestService httpRequestService;
    @Value("${redeemClaimURL}")
    private String redeemClaimURL;

    @Transactional
    public void addClaim(int result, Game g){
        Game game = gameRepo.findById(g.getGameId()).orElseThrow(()->new IllegalStateException("Game not found"));
        List<Ticket> tickets = game.getTickets();
        if(tickets == null)
            return;
        for(Ticket ticket : tickets){
            String user = ticket.getUser();
            for(Bet bet : ticket.getBets()){
                List<Integer> betElements = calculateResultService.getElements(bet.getBetName(), bet.getValue());
                if(betElements.contains(result)){
                    double payout = calculateResultService.getXTimes(bet.getBetName()) * bet.getAmount();
                    claimBetRepository.save(new ClaimBet(false,payout,bet,game,ticket,user));
                }
            }
        }
    }

    @Transactional
    public List<ClaimDTO> getClaim(String email){
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        List<ClaimBet> claimBets = claimBetRepository.findAllByUsername(email).orElseThrow(() -> new UsernameNotFoundException("User not found in Claim Bets repository"));
        return claimBets.stream().filter(c -> !c.isClaimed()).map(Mapper::toClaimDTO).toList();
    }

    @Transactional
    public double redeemClaim(long claimId, String email){
        ClaimBet claimBet = claimBetRepository.findById(claimId).orElseThrow(() -> new IllegalStateException("ClaimId not found"));
        if(claimBet.isClaimed())
            throw new IllegalStateException("Already claimed");

        AccountRedeemClaimPayload accountRedeemClaimPayload = new AccountRedeemClaimPayload(claimBet.getAmount(), "spin-claimId:"+claimId);
        ResponseEntity<Double> response = httpRequestService.sendPostRequestToRedeemClaim(accountRedeemClaimPayload);
        if(response.getStatusCode()!= HttpStatus.OK)
            throw new RuntimeException("unable to claim");
        claimBet.setClaimed(true);
        claimBetRepository.save(claimBet);
        return response.getBody();
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization","Bearer "+tokenService.getAuthTokenFromRequest());
//        HttpEntity<String> entity = new HttpEntity(new AccountRedeemClaimPayload(email,claimBet.getAmount(),"claimId:"+claimId),headers);
//        ResponseEntity<Double> response = restTemplate.exchange(redeemClaimURL, HttpMethod.POST, entity, Double.class);
//        if(response.getStatusCode()!= HttpStatus.OK)
//            throw new RuntimeException("unable to claim");
//        claimBet.setClaimed(true);
//        claimBetRepository.save(claimBet);
//        return response.getBody();
    }
}

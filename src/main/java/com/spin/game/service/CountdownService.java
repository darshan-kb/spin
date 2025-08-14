package com.spin.game.service;

//import com.netflix.discovery.converters.Auto;
import com.spin.game.config.beans.Countdown;
import com.spin.game.entities.Game;
import com.spin.game.model.CountDownModel;
import com.spin.game.repository.GameRepo;
import com.spin.game.service.websocketservice.WebsocketCountdownService;
import com.spin.game.service.websocketservice.WebsocketQueueService;
import com.spin.game.service.websocketservice.WebsocketResultService;
import com.spin.game.serviceclass.ValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CountdownService {
    private final int countdown;
    private final int initcount;
    private int varcount;
    private int result;
    private Game currentGame;
    //private ValueMap valueMap;
    @Autowired
    Countdown countdownbean;
    @Autowired
    private WebsocketCountdownService websocketCountdownService;
    @Autowired
    private WebsocketResultService websocketResultService;
    @Autowired
    GameRepo gameRepo;

    @Autowired
    SseService sseService;
    @Autowired
    ClaimService claimService;
    @Autowired
    WebsocketQueueService websocketQueueService;

    private CalculateResultService calculateResultService;
    @Autowired
    private InitGameService initgame;
    public CountdownService(@Value("${countdown}") int countdown, @Value("${initcount}") int initcount, CalculateResultService calculateResultService){
        this.countdown = countdown;
        this.initcount = initcount;
        varcount = countdown;
        this.calculateResultService = calculateResultService;

    }
    @Scheduled(fixedRate = 1000)
    public void countdownStart(){
        if(countdownbean.getCountdown() == countdown){
            Game cGame = initgame.gameInit();
            setCurrentGame(cGame);
            websocketQueueService.sendQueue();
            //valueMap = new ValueMap();
        }
        //sseService.sendEvents(varcount);
        System.out.println(countdownbean.getCountdown());
        if(countdownbean.getCountdown()==0){
            claimService.addClaim(currentGame.getResultValue(),getCurrentGame());
            //sseService.sendResult(currentGame.getResultValue());
            websocketResultService.sendResult(currentGame.getResultValue());
        }
        //varcount -= 1;
        websocketCountdownService.sendCountdown();
        countdownbean.decrement();

        if(countdownbean.getCountdown() == 7){
            //result = calculateResultService.getCurrentGameResult(getCurrentGame().getGameId());
            result = calculateResultService.getCurrentGameResultByValueMap(getCurrentGame().getGameId());
            currentGame.setResultValue(result);
            gameRepo.save(currentGame);
        }


        if(countdownbean.getCountdown() == initcount){
            countdownbean.reset();
            calculateResultService.markGameAsOver(currentGame);
            calculateResultService.updateQueue(currentGame);
            //varcount = countdown;
        }
    }



    public int getVarCount(){
        return varcount;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public int getResult(){
        return result;
    }
}

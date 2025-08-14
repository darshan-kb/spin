package com.spin.game.controller;

import com.spin.game.model.request.BetTransactionRequest;
import com.spin.game.model.response.GameResponse;
import com.spin.game.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket/v2")
@Slf4j
public class TicketControllerV2 {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/add")
    public ResponseEntity<GameResponse> addTicket(@RequestBody BetTransactionRequest betTransactionRequest) {
        log.info("Adding ticket for txnId : {} and username : {}", betTransactionRequest.getTxnId(), betTransactionRequest.getUserDetails().getUsername());
        GameResponse gameResponse = ticketService.addTicket(betTransactionRequest);
        return new ResponseEntity<>(gameResponse, HttpStatus.OK);
    }
}
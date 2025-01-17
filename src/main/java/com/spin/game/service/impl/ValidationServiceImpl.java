package com.spin.game.service.impl;


import com.spin.game.config.beans.Countdown;
import com.spin.game.model.request.BetTransactionRequest;
import com.spin.game.service.CountdownService;
import com.spin.game.service.TicketService;
import com.spin.game.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Value("${drawCloseTime}")
    private int drawCloseTime;
    @Autowired
    Countdown countdown;
    @Autowired
    @Lazy
    TicketService ticketService;
    @Override
    public boolean validateBetTransactionRequest(BetTransactionRequest betTransactionRequest) {
        return countdown.getCountdown() > drawCloseTime && ticketService.calculateTotalAmount(betTransactionRequest)>0;
    }
}

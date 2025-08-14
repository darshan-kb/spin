package com.spin.game.service;

import com.spin.game.model.TicketModel;
import com.spin.game.model.request.BetTransactionRequest;
import com.spin.game.model.response.GameResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TicketService {
    public double saveTicket(String email, List<List<Integer>> BoardValues);
    public GameResponse addTicket(BetTransactionRequest betTransactionRequest);
    public Integer calculateTotalAmount(BetTransactionRequest betTransactionRequest);
}

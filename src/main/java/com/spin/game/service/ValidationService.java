package com.spin.game.service;

import com.spin.game.model.request.BetTransactionRequest;

public interface ValidationService {
    public boolean validateBetTransactionRequest(BetTransactionRequest betTransactionRequest);
}

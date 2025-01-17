package com.spin.game.controller;

import com.spin.game.model.request.BetTransactionRequest;
import com.spin.game.service.ValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validate")
public class ValidationController {
    @Autowired
    private ValidationService validationService;

    @PostMapping("/betTxn")
    public ResponseEntity<Boolean> validateBetRequest(@RequestBody @Valid BetTransactionRequest betTransactionRequest){
        return new ResponseEntity<>(validationService.validateBetTransactionRequest(betTransactionRequest), HttpStatus.OK);
    }
}

package com.spin.game.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BetDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = -3820303781100522028L;
    private JsonNode betValues;
    private BigDecimal transactionAmount;
}

package com.spin.game.model.response;

import com.spin.game.enums.GameName;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GameResponse {
    private String gameId;
    private String status;
    private GameName gameName;
    private String externalId;
    private BigDecimal totalAmount;
    private String message;
}

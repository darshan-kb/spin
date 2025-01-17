package com.spin.game.model.request;

import com.spin.game.model.BetDetails;
import com.spin.game.model.GameDetails;
import com.spin.game.model.UserDetails;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BetTransactionRequest implements Serializable {

    private static final long serialVersionUID = 4671516273718341839L;

    private UserDetails userDetails;
    private GameDetails gameDetails;
    private BetDetails betDetails;
    @NotNull
    private String txnId;
}

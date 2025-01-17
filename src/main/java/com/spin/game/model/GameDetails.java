package com.spin.game.model;

import com.spin.game.enums.GameName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class GameDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 2489229407492659670L;
    private GameName gameName;
}

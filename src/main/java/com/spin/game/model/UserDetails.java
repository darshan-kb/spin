package com.spin.game.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = 4582186403738158794L;
    private String username;
}

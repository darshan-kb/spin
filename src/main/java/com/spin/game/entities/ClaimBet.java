package com.spin.game.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class ClaimBet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long claimBetId;
    private boolean claimed;
    private double amount;
    @OneToOne
    private Bet bet;
    @ManyToOne
    private Game game;
    @ManyToOne
    private Ticket ticket;
//    @ManyToOne
//    private User user;
    private String username;

    public ClaimBet(boolean claimed, double amount, Bet bet, Game game, Ticket ticket, String username) {
        this.claimed = claimed;
        this.amount = amount;
        this.bet = bet;
        this.game = game;
        this.ticket = ticket;
        this.username = username;
    }
}

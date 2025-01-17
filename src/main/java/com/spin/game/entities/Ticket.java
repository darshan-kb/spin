package com.spin.game.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
//@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ticketId;

    private LocalDateTime timestamp;

    private double totalAmount;

    @ManyToOne
    private Game game;
    private String username;
    private String externalId;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE)
    private List<Bet> bets;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE)
    private List<ClaimBet> claimBets;

    public Ticket() {
    }

    public Ticket(long ticketId, LocalDateTime timestamp, double totalAmount, Game game, String username) {
        this.ticketId = ticketId;
        this.timestamp = timestamp;
        this.totalAmount = totalAmount;
        this.game = game;
        this.username = username;
    }

    public Ticket(long ticketId, LocalDateTime timestamp, double totalAmount, Game game, String username, List<Bet> bets) {
        this.ticketId = ticketId;
        this.timestamp = timestamp;
        this.totalAmount = totalAmount;
        this.game = game;
        this.username = username;
        this.bets = bets;
    }

    public Ticket(LocalDateTime timestamp, double totalAmount, Game game, String username) {
        this.timestamp = timestamp;
        this.totalAmount = totalAmount;
        this.game = game;
        this.username = username;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", timestamp=" + timestamp +
                ", totalAmount=" + totalAmount +
//                ", game=" + game +
                ", user=" + username +
                ", bets=" + bets +
                '}';
    }
}
package com.spin.game.repository;

import com.spin.game.entities.Ticket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    List<Ticket> findAllByUsername(String username, Pageable page);
    long countByUsername(String username);
}

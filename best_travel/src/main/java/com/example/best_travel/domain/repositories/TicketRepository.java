package com.example.best_travel.domain.repositories;

import com.example.best_travel.domain.entities.Ticket;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

}

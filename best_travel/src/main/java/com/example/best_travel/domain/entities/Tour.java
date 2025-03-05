package com.example.best_travel.domain.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Tour {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ToString.Exclude
  @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<Reservation> reservations;

  @ToString.Exclude
  @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<Ticket> tickets;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_customer")
  private Customer customer;

  @Override
  public String toString() {
    return String.format("Tour[id=%d, customer=%s]", id, customer);
  }

  public void addTicket(Ticket ticket) {
    if (Objects.isNull(this.tickets)) {
      this.tickets = new HashSet<>();
    }
    tickets.add(ticket);
  }

  public void removeTicket(UUID ticketId) {
    this.tickets.removeIf(ticket -> ticket.getId().equals(ticketId));
  }

/*  @PreRemove
  @PreUpdate
  @PrePersist*/
  public void updateTickets() {
    this.tickets.forEach(ticket -> ticket.setTour(this));
  }

  public void addReservation(Reservation reservation) {
    if (Objects.isNull(this.reservations)) {
      this.reservations = new HashSet<>();
    }
    reservations.add(reservation);
  }

}

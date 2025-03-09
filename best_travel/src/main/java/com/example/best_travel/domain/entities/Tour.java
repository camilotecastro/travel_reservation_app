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
import java.util.HashSet;
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

  @PrePersist
  @PreRemove
  public void updateTickets() {
    this.tickets.forEach(ticket -> ticket.setTour(this));
    this.reservations.forEach(reservation -> reservation.setTour(this));
  }

  public void removeTicket(UUID id) {
    this.tickets.removeIf(ticket -> ticket.getId().equals(id));
  }

  public void removeReservation(UUID id) {
    this.reservations.removeIf(reservation -> reservation.getId().equals(id));
  }

  public void addTicket(Ticket ticket) {
    if (Objects.isNull(this.tickets)) {
      this.tickets = new HashSet<>();
    }
    this.tickets.add(ticket);
    this.tickets.forEach(t -> ticket.setTour(this));
  }

  public void addReservation(Reservation reservation) {
    if (Objects.isNull(this.reservations)) {
      this.reservations = new HashSet<>();
    }
    this.reservations.add(reservation);
    this.reservations.forEach(r -> reservation.setTour(this));
  }


  @Override
  public String toString() {
    return String.format("Tour[id=%d, customer=%s]", id, customer);
  }


}

package com.example.best_travel.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Ticket {

  @Id
  private UUID id;
  private LocalDate departureDate;
  private LocalDate arrivalDate;
  private LocalDate purchaseDate;
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "fly_id")
  private Fly fly;
  @ManyToOne
  @JoinColumn(name = "tour_id")
  private Tour tour;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;


}

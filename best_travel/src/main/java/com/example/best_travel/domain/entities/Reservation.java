package com.example.best_travel.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class Reservation implements Serializable {

  @Id
  private UUID id;

  @Column(name = "date_reservation")
  private LocalDateTime dateTimeReservation;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private Integer totalDays;
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;

  @ManyToOne
  @JoinColumn(name = "tour_id")
  private Tour tour;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

}

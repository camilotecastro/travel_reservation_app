package com.example.best_travel.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reservation implements Serializable {

  @EqualsAndHashCode.Include
  @Id
  private UUID id;

  @Column(name = "date_reservation")
  private LocalDateTime dateTimeReservation;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private Integer totalDays;
  private BigDecimal price;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tour_id")
  private Tour tour;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Override
  public String toString() {
    return String.format("Reservation[id=%s, dateStart=%s, dateEnd=%s, price=%s]",
        id, dateStart, dateEnd, price);
  }



}

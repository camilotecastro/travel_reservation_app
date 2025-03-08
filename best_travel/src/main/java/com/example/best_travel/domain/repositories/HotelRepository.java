package com.example.best_travel.domain.repositories;

import com.example.best_travel.domain.entities.Hotel;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

  Set<Hotel> findByPriceLessThan(BigDecimal price);
  Set<Hotel> findByPriceIsBetween(BigDecimal minPrice, BigDecimal maxPrice);
  Set<Hotel> findByRatingGreaterThan(Integer rating);

  @Query("SELECT h FROM Hotel h JOIN h.reservations r WHERE r.id = :reservationId")
  Optional<Hotel> findByReservationId(UUID reservationId);

}

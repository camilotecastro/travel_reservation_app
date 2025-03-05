package com.example.best_travel.domain.repositories;

import com.example.best_travel.domain.entities.Fly;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlyRepository extends JpaRepository<Fly, Long> {

  // tipos de querys, jpql, native query, query methods

  // query method
  @Query("SELECT f FROM Fly f WHERE f.price < :price")
  Set<Fly> selectLessPrice(BigDecimal price);

  // query method
  @Query("SELECT f FROM Fly f WHERE f.price BETWEEN :minPrice AND :maxPrice")
  Set<Fly> selectBetweenPrice(BigDecimal minPrice, BigDecimal maxPrice);

  // query method
  @Query("SELECT f FROM Fly f WHERE f.originName = :origin AND f.destinyName = :destiny")
  Set<Fly> selectOriginDestiny(String origin, String destiny);

  // JOIN ticket ID
  @Query("SELECT f FROM Fly f JOIN FETCH f.tickets t WHERE t.id = :ticketId")
  Optional<Fly> selectByTicketId(UUID ticketId);



}

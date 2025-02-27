package com.example.best_travel.domain.repositories;

import com.example.best_travel.domain.entities.Reservation;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, UUID> {

}

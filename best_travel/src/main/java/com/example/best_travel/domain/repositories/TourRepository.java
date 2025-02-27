package com.example.best_travel.domain.repositories;

import com.example.best_travel.domain.entities.Tour;
import org.springframework.data.repository.CrudRepository;

public interface TourRepository extends CrudRepository<Tour, Long> {

}

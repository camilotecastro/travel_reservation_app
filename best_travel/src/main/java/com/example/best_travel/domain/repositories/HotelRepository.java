package com.example.best_travel.domain.repositories;

import com.example.best_travel.domain.entities.Hotel;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<Hotel, Long> {

}

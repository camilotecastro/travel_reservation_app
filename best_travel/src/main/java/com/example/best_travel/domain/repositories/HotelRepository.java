package com.example.best_travel.domain.repositories;

import com.example.best_travel.domain.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}

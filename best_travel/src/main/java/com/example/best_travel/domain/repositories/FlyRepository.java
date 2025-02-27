package com.example.best_travel.domain.repositories;

import com.example.best_travel.domain.entities.Fly;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlyRepository extends JpaRepository<Fly, Long> {

}

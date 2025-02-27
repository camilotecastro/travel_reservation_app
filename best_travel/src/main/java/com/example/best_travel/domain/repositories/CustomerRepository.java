package com.example.best_travel.domain.repositories;

import com.example.best_travel.domain.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {

}

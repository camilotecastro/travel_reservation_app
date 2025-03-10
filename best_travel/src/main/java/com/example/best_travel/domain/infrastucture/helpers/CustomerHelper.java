package com.example.best_travel.domain.infrastucture.helpers;

import com.example.best_travel.domain.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Helper class for Customer entity to increment the total of tours, flights and lodgings.
 */
@Transactional
@Component
@AllArgsConstructor
public class CustomerHelper {

  private final CustomerRepository customerRepository;

  public void increment(String customerId, Class<?> type) {
    var customer = this.customerRepository.findById(customerId).orElseThrow();
    switch (type.getSimpleName()) {
      case "TourService" -> customer.setTotalTours(customer.getTotalTours() + 1);
      case "TicketService" -> customer.setTotalFlights(customer.getTotalFlights() + 1);
      case "ReservationService" -> customer.setTotalLodgings(customer.getTotalLodgings() + 1);
    }
    this.customerRepository.save(customer);
  }

}

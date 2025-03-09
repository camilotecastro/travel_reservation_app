package com.example.best_travel.domain.infrastucture.helpers;

import com.example.best_travel.domain.entities.Customer;
import com.example.best_travel.domain.entities.Fly;
import com.example.best_travel.domain.entities.Hotel;
import com.example.best_travel.domain.entities.Reservation;
import com.example.best_travel.domain.entities.Ticket;
import com.example.best_travel.domain.infrastucture.services.ReservationService;
import com.example.best_travel.domain.infrastucture.services.TicketService;
import com.example.best_travel.domain.repositories.HotelRepository;
import com.example.best_travel.domain.repositories.ReservationRepository;
import com.example.best_travel.domain.repositories.TicketRepository;
import com.example.best_travel.util.BestTravelUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Helper class for Tour entity create tickets and reservations for it.
 */
@Transactional
@Component
@AllArgsConstructor
public class TourHelper {

  private final TicketRepository ticketRepository;
  private final HotelRepository hotelRepository;
  private final ReservationRepository reservationRepository;

  public Set<Ticket> createTickets(Set<Fly> flights, Customer customer) {
    var response = new HashSet<Ticket>(flights.size());
    flights.forEach(fly -> {
      var ticketToPersist = Ticket.builder()
          .id(UUID.randomUUID())
          .fly(fly)
          .customer(customer)
          .price(
              fly.getPrice().add(fly.getPrice().multiply(TicketService.CHARGER_PRICE_PERCENTAGE)))
          .purchaseDate(BestTravelUtil.randomDate())
          .arrivalDate(BestTravelUtil.randomDate())
          .departureDate(BestTravelUtil.randomDate())
          .build();
      response.add(this.ticketRepository.save(ticketToPersist));
    });
    return response;
  }

  public Set<Reservation> createReservation(HashMap<Hotel, Integer> hotels, Customer customer) {
    var response = new HashSet<Reservation>(hotels.size());
    hotels.forEach((hotel, totalDays) -> {
      var reservationToPersist = Reservation.builder()
          .id(UUID.randomUUID())
          .hotel(hotel)
          .customer(customer)
          .totalDays(totalDays)
          .dateStart(LocalDate.now())
          .dateTimeReservation(LocalDateTime.now())
          .dateEnd(LocalDate.now().plusDays(totalDays))
          .price(BigDecimal.valueOf(
              hotel.getPrice() + (hotel.getPrice() * ReservationService.CHARGES_PRICE_PERCENTAGE)))
          .build();
      response.add(this.reservationRepository.save(reservationToPersist));
    });

    return response;
  }

  public Ticket createTicket(Fly fly, Customer customer) {
    var ticketToPersist = Ticket.builder()
        .id(UUID.randomUUID())
        .fly(fly)
        .customer(customer)
        .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.CHARGER_PRICE_PERCENTAGE)))
        .purchaseDate(BestTravelUtil.randomDate())
        .arrivalDate(BestTravelUtil.randomDate())
        .departureDate(BestTravelUtil.randomDate())
        .build();

    return this.ticketRepository.save(ticketToPersist);
  }

  public Reservation createReservation(Hotel hotel, Customer customer, Integer totalDays) {

    var reservationToPersist = Reservation.builder()
        .id(UUID.randomUUID())
        .hotel(hotel)
        .customer(customer)
        .totalDays(totalDays)
        .dateStart(LocalDate.now())
        .dateTimeReservation(LocalDateTime.now())
        .dateEnd(LocalDate.now().plusDays(1))
        .price(BigDecimal.valueOf(
            hotel.getPrice() + (hotel.getPrice() * ReservationService.CHARGES_PRICE_PERCENTAGE)))
        .build();

    return this.reservationRepository.save(reservationToPersist);
  }

}

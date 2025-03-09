package com.example.best_travel.domain.infrastucture.services;

import com.example.best_travel.api.models.request.TourRequest;
import com.example.best_travel.api.models.response.TourResponse;
import com.example.best_travel.domain.entities.Fly;
import com.example.best_travel.domain.entities.Hotel;
import com.example.best_travel.domain.entities.Reservation;
import com.example.best_travel.domain.entities.Ticket;
import com.example.best_travel.domain.entities.Tour;
import com.example.best_travel.domain.infrastucture.abstractservices.ITourService;
import com.example.best_travel.domain.infrastucture.helpers.CustomerHelper;
import com.example.best_travel.domain.infrastucture.helpers.TourHelper;
import com.example.best_travel.domain.repositories.CustomerRepository;
import com.example.best_travel.domain.repositories.FlyRepository;
import com.example.best_travel.domain.repositories.HotelRepository;
import com.example.best_travel.domain.repositories.TourRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AllArgsConstructor
@Service
public class TourService implements ITourService {

  private final TourRepository tourRepository;
  private final FlyRepository flyRepository;
  private final HotelRepository hotelRepository;
  private final CustomerRepository customerRepository;
  private final TourHelper tourHelper;
  private final CustomerHelper customerHelper;

  @Override
  public TourResponse create(TourRequest request) {
    var customer = customerRepository.findById(request.getCustomerId()).orElseThrow();

    var flights = new HashSet<Fly>();
    request.getFlights().forEach(flight -> {
      flights.add(this.flyRepository.findById(flight.getId()).orElseThrow());
    });

    var hotels = new HashMap<Hotel, Integer>();
    request.getHotels().forEach(hotel -> {
      hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(), hotel.getTotalDays());
    });

    var tourSaved = Tour.builder()
        .tickets(this.tourHelper.createTickets(flights, customer))
        .reservations(this.tourHelper.createReservation(hotels, customer))
        .customer(customer)
        .build();

    var tourPersisted = this.tourRepository.save(tourSaved);

    this.customerHelper.increment(customer.getDni(), TourService.class);

    return TourResponse.builder()
        .id(tourPersisted.getId())
        .reservationIds(tourPersisted.getReservations().stream().map(Reservation::getId).collect(
            Collectors.toSet()))
        .ticketIds(
            tourPersisted.getTickets().stream().map(Ticket::getId).collect(Collectors.toSet()))
        .build();
  }

  @Override
  public TourResponse read(Long id) {
    var tourFromDb = this.tourRepository.findById(id).orElseThrow();
    return TourResponse.builder()
        .id(tourFromDb.getId())
        .reservationIds(tourFromDb.getReservations().stream().map(Reservation::getId).collect(
            Collectors.toSet()))
        .ticketIds(tourFromDb.getTickets().stream().map(Ticket::getId).collect(Collectors.toSet()))
        .build();
  }

  @Override
  public void delete(Long Long) {
    var tourToDelete = this.tourRepository.findById(Long).orElseThrow();
    this.tourRepository.delete(tourToDelete);
  }

  @Override
  public void removeTicket(Long tourId, UUID ticketId) {
    var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
    tourUpdate.removeTicket(ticketId);
    this.tourRepository.save(tourUpdate);
  }

  @Override
  public UUID addTicket(Long flyId, Long tourId) {
    var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
    var fly = this.flyRepository.findById(flyId).orElseThrow();
    var ticket = this.tourHelper.createTicket(fly, tourUpdate.getCustomer());
    tourUpdate.addTicket(ticket);
    this.tourRepository.save(tourUpdate);
    return ticket.getId();
  }

  @Override
  public void removeReservation(Long tourId, UUID reservationId) {
    var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
    tourUpdate.removeReservation(reservationId);
    this.tourRepository.save(tourUpdate);

  }

  @Override
  public UUID addReservation(Long tourId, Long hotelId, Integer totalDays) {
    var tourUpdate = this.tourRepository.findById(tourId).orElseThrow();
    var hotel = this.hotelRepository.findById(hotelId).orElseThrow();
    var reservation = this.tourHelper.createReservation(hotel, tourUpdate.getCustomer(), totalDays);
    tourUpdate.addReservation(reservation);
    this.tourRepository.save(tourUpdate);
    return reservation.getId();
  }


}

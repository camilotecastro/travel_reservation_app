package com.example.best_travel.domain.infrastucture.services;

import static java.util.Objects.nonNull;

import com.example.best_travel.api.models.request.TourRequest;
import com.example.best_travel.api.models.response.TourResponse;
import com.example.best_travel.domain.entities.Fly;
import com.example.best_travel.domain.entities.Hotel;
import com.example.best_travel.domain.entities.Reservation;
import com.example.best_travel.domain.entities.Ticket;
import com.example.best_travel.domain.entities.Tour;
import com.example.best_travel.domain.infrastucture.abstractservices.ITourService;
import com.example.best_travel.domain.infrastucture.helpers.BlackListHelper;
import com.example.best_travel.domain.infrastucture.helpers.CustomerHelper;
import com.example.best_travel.domain.infrastucture.helpers.MailHelper;
import com.example.best_travel.domain.infrastucture.helpers.TourHelper;
import com.example.best_travel.domain.repositories.CustomerRepository;
import com.example.best_travel.domain.repositories.FlyRepository;
import com.example.best_travel.domain.repositories.HotelRepository;
import com.example.best_travel.domain.repositories.TourRepository;
import com.example.best_travel.util.enums.Tables;
import com.example.best_travel.util.exceptions.IdNotFoundException;
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
  private final BlackListHelper blackListHelper;
  private final MailHelper emailHelper;

  @Override
  public TourResponse create(TourRequest request) {

    blackListHelper.isInBlackList(request.getCustomerId());

    var customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
        () -> new IdNotFoundException(Tables.customer.name()));

    var flights = new HashSet<Fly>();
    request.getFlights().forEach(flight ->
        flights.add(this.flyRepository.findById(flight.getId())
            .orElseThrow(() -> new IdNotFoundException(Tables.fly.name()))));

    var hotels = new HashMap<Hotel, Integer>();
    request.getHotels().forEach(hotel -> {
      hotels.put(this.hotelRepository.findById(hotel.getId()).orElseThrow(
          () -> new IdNotFoundException(Tables.hotel.name())), hotel.getTotalDays());
    });

    var tourSaved = Tour.builder()
        .tickets(this.tourHelper.createTickets(flights, customer))
        .reservations(this.tourHelper.createReservation(hotels, customer))
        .customer(customer)
        .build();

    var tourPersisted = this.tourRepository.save(tourSaved);

    this.customerHelper.increment(customer.getDni(), TourService.class);

    if (nonNull(request.getEmail())) {
      emailHelper.sendEmail(request.getEmail(), customer.getFullName(), Tables.tour.name());
    }

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
    var tourFromDb = this.tourRepository.findById(id).orElseThrow(
        () -> new IdNotFoundException(Tables.tour.name()));
    return TourResponse.builder()
        .id(tourFromDb.getId())
        .reservationIds(tourFromDb.getReservations().stream().map(Reservation::getId).collect(
            Collectors.toSet()))
        .ticketIds(tourFromDb.getTickets().stream().map(Ticket::getId).collect(Collectors.toSet()))
        .build();
  }

  @Override
  public void delete(Long Long) {
    var tourToDelete = this.tourRepository.findById(Long).orElseThrow(
        () -> new IdNotFoundException(Tables.tour.name()));
    this.tourRepository.delete(tourToDelete);
  }

  @Override
  public void removeTicket(Long tourId, UUID ticketId) {
    var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(
        () -> new IdNotFoundException(Tables.tour.name()));
    tourUpdate.removeTicket(ticketId);
    this.tourRepository.save(tourUpdate);
  }

  @Override
  public UUID addTicket(Long flyId, Long tourId) {
    var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(
        () -> new IdNotFoundException(Tables.tour.name()));
    var fly = this.flyRepository.findById(flyId).orElseThrow(
        () -> new IdNotFoundException(Tables.fly.name()));
    var ticket = this.tourHelper.createTicket(fly, tourUpdate.getCustomer());
    tourUpdate.addTicket(ticket);
    this.tourRepository.save(tourUpdate);
    return ticket.getId();
  }

  @Override
  public void removeReservation(Long tourId, UUID reservationId) {
    var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(
        () -> new IdNotFoundException(Tables.tour.name()));
    tourUpdate.removeReservation(reservationId);
    this.tourRepository.save(tourUpdate);

  }

  @Override
  public UUID addReservation(Long tourId, Long hotelId, Integer totalDays) {
    var tourUpdate = this.tourRepository.findById(tourId).orElseThrow(
        () -> new IdNotFoundException(Tables.tour.name()));
    var hotel = this.hotelRepository.findById(hotelId).orElseThrow(
        () -> new IdNotFoundException(Tables.hotel.name()));
    var reservation = this.tourHelper.createReservation(hotel, tourUpdate.getCustomer(), totalDays);
    tourUpdate.addReservation(reservation);
    this.tourRepository.save(tourUpdate);
    return reservation.getId();
  }


}

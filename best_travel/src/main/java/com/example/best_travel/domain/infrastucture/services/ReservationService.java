package com.example.best_travel.domain.infrastucture.services;

import com.example.best_travel.api.models.request.ReservationRequest;
import com.example.best_travel.api.models.response.HotelResponse;
import com.example.best_travel.api.models.response.ReservationResponse;
import com.example.best_travel.domain.entities.Reservation;
import com.example.best_travel.domain.infrastucture.abstractservices.IReservationService;
import com.example.best_travel.domain.repositories.CustomerRepository;
import com.example.best_travel.domain.repositories.HotelRepository;
import com.example.best_travel.domain.repositories.ReservationRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Transactional
@Service
public class ReservationService implements IReservationService {

  private static final double CHARGES_PRICE_PERCENTAGE = 0.20;

  private final ReservationRepository reservationRepository;
  private final HotelRepository hotelRepository;
  private final CustomerRepository customerRepository;

  @Override
  public ReservationResponse create(ReservationRequest request) {
    var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow();
    var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

    var reservationToPersist = Reservation.builder()
        .id(UUID.randomUUID())
        .hotel(hotel)
        .customer(customer)
        .totalDays(request.getTotalDays())
        .dateStart(LocalDate.now())
        .dateTimeReservation(LocalDateTime.now())
        .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
        .price(BigDecimal.valueOf(hotel.getPrice() + (hotel.getPrice() * CHARGES_PRICE_PERCENTAGE)))
        .build();

    var reservationPersisted = this.reservationRepository.save(reservationToPersist);

    return this.entityToResponse(reservationPersisted);
  }


  @Override
  public ReservationResponse read(UUID uuid) {
    var reservation = this.reservationRepository.findById(uuid).orElseThrow();
    return this.entityToResponse(reservation);
  }

  @Override
  public ReservationResponse update(UUID uuid, ReservationRequest request) {
    var customer = customerRepository.findById(request.getIdClient()).orElseThrow();
    var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow();

    var reservationToUpdate = this.reservationRepository.findById(uuid).orElseThrow();
    reservationToUpdate.setHotel(hotel);
    reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
    // Not update customer.
    //reservationToUpdate.setCustomer(customer);
    reservationToUpdate.setTotalDays(request.getTotalDays());
    reservationToUpdate.setDateStart(LocalDate.now());
    reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
    reservationToUpdate.setPrice(BigDecimal.valueOf(hotel.getPrice() + (hotel.getPrice() * CHARGES_PRICE_PERCENTAGE)));

    log.info("Updating reservation {}", reservationToUpdate.getId());

    var reservationPersisted = this.reservationRepository.save(reservationToUpdate);

    return this.entityToResponse(reservationPersisted);
  }

  @Override
  public void delete(UUID uuid) {
    var reservation = this.reservationRepository.findById(uuid).orElseThrow();
    this.reservationRepository.delete(reservation);
  }

  @Override
  public BigDecimal findPrice(Long hotelId) {
    var hotel = hotelRepository.findById(hotelId).orElseThrow();
    return BigDecimal.valueOf(hotel.getPrice() + (hotel.getPrice() * CHARGES_PRICE_PERCENTAGE));
  }

  private ReservationResponse entityToResponse(Reservation entity) {
    var reservationResponse = new ReservationResponse();
    BeanUtils.copyProperties(entity, reservationResponse);

    var hotelResponse = new HotelResponse();
    BeanUtils.copyProperties(entity.getHotel(), hotelResponse, "reservations");

    reservationResponse.setHotel(hotelResponse);
    return reservationResponse;
  }

}

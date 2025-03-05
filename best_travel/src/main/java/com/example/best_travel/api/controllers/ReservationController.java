package com.example.best_travel.api.controllers;

import com.example.best_travel.api.models.request.ReservationRequest;
import com.example.best_travel.api.models.response.ReservationResponse;
import com.example.best_travel.api.models.response.TicketResponse;
import com.example.best_travel.domain.infrastucture.abstractservices.IReservationService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/reservation")
public class ReservationController {

  private final IReservationService reservationService;


  @GetMapping("/{id}")
  public ResponseEntity<ReservationResponse> getReservation(@PathVariable UUID id) {
    return ResponseEntity.ok(reservationService.read(id));
  }

  @GetMapping
  public ResponseEntity<Map<String, BigDecimal>> getTicket(@RequestParam Long reservationId) {
    return ResponseEntity.ok(
        Collections.singletonMap("ticketPrice", reservationService.findPrice(reservationId)));
  }

  @PostMapping
  public ResponseEntity<ReservationResponse> postReservation(
      @RequestBody ReservationRequest reservationRequest) {
    return ResponseEntity.ok(reservationService.create(reservationRequest));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReservationResponse> putReservation(@PathVariable UUID id,
      @RequestBody ReservationRequest reservationRequest) {
    return ResponseEntity.ok(reservationService.update(id, reservationRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
    reservationService.delete(id);
    return ResponseEntity.noContent().build();
  }

}


package com.example.best_travel.api.controllers;

import com.example.best_travel.api.models.request.ReservationRequest;
import com.example.best_travel.api.models.response.ErrorsResponse;
import com.example.best_travel.api.models.response.ReservationResponse;
import com.example.best_travel.domain.infrastucture.abstractservices.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/reservation")
@Tag(name = "Reservation", description = "Reservation API")
public class ReservationController {

  private final IReservationService reservationService;


  @Operation(summary = "Get a reservation by id")
  @GetMapping("/{id}")
  public ResponseEntity<ReservationResponse> getReservation(@PathVariable UUID id) {
    return ResponseEntity.ok(reservationService.read(id));
  }

  @Operation(summary = "Get the price of a reservation")
  @GetMapping
  public ResponseEntity<Map<String, BigDecimal>> getReservationPrice(
      @RequestParam Long hotelId,
      @RequestHeader(required = false) Currency currency) {

    if (Objects.isNull(currency)) {
      currency = Currency.getInstance("USD");
    }

    return ResponseEntity.ok(
        Collections.singletonMap("ticketPrice", reservationService.findPrice(hotelId, currency)));
  }

  @ApiResponse(responseCode = "400",
      description = "Invalid reservation data",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorsResponse.class))
  )
  @Operation(summary = "Create a new reservation")
  @PostMapping
  public ResponseEntity<ReservationResponse> postReservation(
      @Valid @RequestBody ReservationRequest reservationRequest) {
    return ResponseEntity.ok(reservationService.create(reservationRequest));
  }

  @Operation(summary = "Update a reservation")
  @PutMapping("/{id}")
  public ResponseEntity<ReservationResponse> putReservation(@PathVariable UUID id,
      @Valid @RequestBody ReservationRequest reservationRequest) {
    return ResponseEntity.ok(reservationService.update(id, reservationRequest));
  }

  @Operation(summary = "Delete a reservation")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
    reservationService.delete(id);
    return ResponseEntity.noContent().build();
  }

}


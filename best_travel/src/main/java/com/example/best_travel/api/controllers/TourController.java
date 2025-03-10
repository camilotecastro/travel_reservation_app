package com.example.best_travel.api.controllers;

import com.example.best_travel.api.models.request.TourRequest;
import com.example.best_travel.api.models.response.ErrorsResponse;
import com.example.best_travel.api.models.response.TourResponse;
import com.example.best_travel.domain.infrastucture.services.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/tour")
@Tag(name = "Tour", description = "Tour API")
public class TourController {

  private final TourService tourService;

  @ApiResponse(responseCode = "400",
      description = "Invalid reservation data",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorsResponse.class))
  )
  @Operation(summary = "Create a new tour")
  @PostMapping()
  public ResponseEntity<TourResponse> createTour(@Valid @RequestBody TourRequest tourRequest) {
    return ResponseEntity.ok(tourService.create(tourRequest));
  }

  @Operation(summary = "Get a tour by id")
  @GetMapping(path = "{id}")
  public ResponseEntity<TourResponse> getTourById(@PathVariable Long id) {
    return ResponseEntity.ok(tourService.read(id));
  }

  @Operation(summary = "Delete a tour by id")
  @DeleteMapping(path = "{id}")
  public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
    tourService.delete(id);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Remove a ticket from a tour")
  @PatchMapping(path = "{tourId}/remove-ticket/{ticketId}")
  public ResponseEntity<Void> deleteTicket(@PathVariable Long tourId, @PathVariable UUID ticketId) {
    tourService.removeTicket(tourId, ticketId);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Add a ticket to a tour")
  @PatchMapping(path = "{tourId}/add-ticket/{flyId}")
  public ResponseEntity<Map<String, UUID>> addTicket(@PathVariable Long tourId,
      @PathVariable Long flyId) {
    var response = Collections.singletonMap("ticketId", tourService.addTicket(flyId, tourId));
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Remove a reservation from a tour")
  @PatchMapping(path = "{tourId}/remove-reservation/{reservationId}")
  public ResponseEntity<Void> deleteReservation(@PathVariable Long tourId,
      @PathVariable UUID reservationId) {
    tourService.removeReservation(tourId, reservationId);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Add a reservation to a tour")
  @PatchMapping(path = "{tourId}/add-reservation/{hotelId}")
  public ResponseEntity<Map<String, UUID>> addReservation(
      @PathVariable Long tourId,
      @PathVariable Long hotelId,
      @RequestParam Integer totalDays) {
    var response = Collections.singletonMap("ticketId", tourService.addReservation(tourId, hotelId,
        totalDays));
    return ResponseEntity.ok(response);
  }

}

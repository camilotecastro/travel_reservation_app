package com.example.best_travel.api.controllers;

import com.example.best_travel.api.models.request.TicketRequest;
import com.example.best_travel.api.models.response.ErrorsResponse;
import com.example.best_travel.api.models.response.TicketResponse;
import com.example.best_travel.domain.infrastucture.abstractservices.ITicketService;
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

@AllArgsConstructor
@RestController
@RequestMapping(path = "/ticket")
@Tag(name = "Ticket", description = "Ticket API")
public class TicketController {

  private final ITicketService ticketService;

  @ApiResponse(responseCode = "400",
      description = "Invalid reservation data",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorsResponse.class))
  )
  @Operation(summary = "Create a new ticket")
  @PostMapping
  public ResponseEntity<TicketResponse> postTicket(
      @Valid @RequestBody TicketRequest ticketRequest) {
    return ResponseEntity.ok(this.ticketService.create(ticketRequest));
  }

  @Operation(summary = "Get a ticket by id")
  @GetMapping("/{id}")
  public ResponseEntity<TicketResponse> getTicket(@PathVariable UUID id) {
    return ResponseEntity.ok(this.ticketService.read(id));
  }

  @Operation(summary = "Get the price of a ticket")
  @GetMapping()
  public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(
      @RequestParam Long flyId,
      @RequestHeader(required = false) Currency currency) {

    if (Objects.isNull(currency)) {
      currency = Currency.getInstance("USD");
    }

    return ResponseEntity.ok(
        Collections.singletonMap("flyPrice", this.ticketService.findPrice(flyId, currency)));
  }

  @Operation(summary = "Update a ticket")
  @PutMapping("/{id}")
  public ResponseEntity<TicketResponse> putTicket(@PathVariable UUID id,
      @Valid @RequestBody TicketRequest ticketRequest) {
    return ResponseEntity.ok(this.ticketService.update(id, ticketRequest));
  }

  @Operation(summary = "Delete a ticket by id")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
    this.ticketService.delete(id);
    return ResponseEntity.noContent().build();
  }

}

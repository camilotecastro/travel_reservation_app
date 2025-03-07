package com.example.best_travel.api.controllers;

import com.example.best_travel.api.models.request.TicketRequest;
import com.example.best_travel.api.models.response.TicketResponse;
import com.example.best_travel.domain.infrastucture.abstractservices.ITicketService;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@RestController
@RequestMapping(path = "/ticket")
public class TicketController {

  private final ITicketService ticketService;

  @PostMapping
  public ResponseEntity<TicketResponse> postTicket(@RequestBody TicketRequest ticketRequest) {
    return ResponseEntity.ok(this.ticketService.create(ticketRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TicketResponse> getTicket(@PathVariable UUID id) {
    return ResponseEntity.ok(this.ticketService.read(id));
  }

  @GetMapping()
  public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam Long flyId) {
    return ResponseEntity.ok(
        Collections.singletonMap("flyPrice", this.ticketService.findPrice(flyId)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TicketResponse> putTicket(@PathVariable UUID id,
      @RequestBody TicketRequest ticketRequest) {
    return ResponseEntity.ok(this.ticketService.update(id, ticketRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
    this.ticketService.delete(id);
    return ResponseEntity.noContent().build();
  }

}

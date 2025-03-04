package com.example.best_travel.api.models.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TicketResponse implements Serializable {

  private UUID id;
  private LocalDate departureDate;
  private LocalDate arrivalDate;
  private LocalDate purchaseDate;
  private BigDecimal price;
  private FlyResponse flyResponse;


}

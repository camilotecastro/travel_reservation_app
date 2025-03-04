package com.example.best_travel.api.models.request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TicketRequest implements Serializable {

  private String idClient;
  private Long idFly;

}

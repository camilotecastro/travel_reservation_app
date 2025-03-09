package com.example.best_travel.api.models.response;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TourResponse implements Serializable {

  private Long id;
  private Set<UUID> ticketIds;
  private Set<UUID> reservationIds;

}

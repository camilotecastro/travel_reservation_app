package com.example.best_travel.api.models.request;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TourRequest implements Serializable {

  private String customerId;
  private Set<FlyRequest> flights;
  private Set<TourHotelRequest> hotels;


}

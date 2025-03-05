package com.example.best_travel.api.models.response;


import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HotelResponse implements Serializable {

  private Long id;
  private String name;
  private String address;
  private BigDecimal price;
  private Integer rating;

}

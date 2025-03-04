package com.example.best_travel.api.models.response;

import com.example.best_travel.util.AeroLine;
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
public class FlyResponse implements Serializable {

  private Long id;
  private Double originLat;
  private Double originLng;
  private Double destinyLat;
  private Double destinyLng;
  private String originName;
  private String destinyName;
  private BigDecimal price;
  private AeroLine aeroLine;


}

package com.example.best_travel.domain.infrastucture.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;
import lombok.Data;

@Data
public class CurreyncyDTO implements Serializable {

  @JsonProperty(value = "date")
  private LocalDate exchangeDate;

  private Map<Currency, BigDecimal> rates;

}

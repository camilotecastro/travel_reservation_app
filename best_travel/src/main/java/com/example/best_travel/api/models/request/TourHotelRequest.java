package com.example.best_travel.api.models.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TourHotelRequest implements Serializable {

  @NotBlank(message = "The id is required")
  @NotNull(message = "The id must not be null")
  @Positive(message = "The id must be a positive number")
  private Long id;

  @NotNull(message = "The totalDays is required")
  @Positive(message = "The totalDays must be a positive number")
  @Min(value = 1, message = "The totalDays must be greater than 1")
  private Integer totalDays;

}

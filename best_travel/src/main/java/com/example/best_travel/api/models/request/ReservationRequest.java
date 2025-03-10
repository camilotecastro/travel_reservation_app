package com.example.best_travel.api.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest {

  @Size(min = 18, max = 20, message = "The idClient must be between 18 and 20 characters")
  @NotBlank(message = "The idClient is required")
  private String idClient;

  @Positive(message = "The idHotel must be a positive number")
  @NotNull(message = "The idHotel must not be null")
  private Long idHotel;

  @Min(value = 1, message = "The totalDays must be greater than 1")
  @Max(value = 30, message = "The totalDays must be less than 30")
  @NotNull(message = "The totalDays is required")
  private Integer totalDays;

  //@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "The email is not valid")
  @Email(message = "The email is not valid")
  private String email;

}

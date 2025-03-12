package com.example.best_travel.api.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

  @NotNull(message = "The customerId must not be null")
  @NotBlank(message = "The customerId is required")
  private String customerId;

  @Size(min = 1, message = "The flights must have at least one element")
  @NotNull
  private Set<FlyRequest> flights;

  @Size(min = 1, message = "The hotels must have at least one element")
  @NotNull
  private Set<TourHotelRequest> hotels;

  //@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "The email is not valid")
  @Email(message = "The email is not valid")
  private String email;


}

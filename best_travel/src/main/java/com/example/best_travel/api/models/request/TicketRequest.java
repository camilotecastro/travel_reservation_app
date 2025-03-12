package com.example.best_travel.api.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

  @NotNull(message = "The idClient is required")
  private String idClient;

  @NotNull(message = "The idHotel is required")
  @NotBlank(message = "The idFly is required")
  @Positive(message = "The idFly must be a positive number")
  private Long idFly;

  //@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "The email is not valid")
  @Email(message = "The email is not valid")
  private String email;

}

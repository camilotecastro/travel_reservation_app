package com.example.best_travel.api.models.request;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FlyRequest implements Serializable {

  @NotNull(message = "The id is required")
  private Long id;

}

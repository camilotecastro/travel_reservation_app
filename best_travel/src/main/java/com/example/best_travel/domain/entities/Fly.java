package com.example.best_travel.domain.entities;

import com.example.best_travel.util.AeroLine;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Fly implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Double originLat;
  private Double originLng;
  private Double destinyLng;
  private Double destinyLat;
  private BigDecimal price;
  @Column(length = 20)
  private String originName;
  @Column(length = 20)
  private String destinyName;
  @Enumerated(EnumType.STRING)
  private AeroLine aeroLine;

  @OneToMany(mappedBy = "fly", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private Set<Ticket> tickets;

  @Override
  public String toString() {
    return String.format("Fly[id=%d, origin=%s, destiny=%s, price=%s, airline=%s]",
        id, originName, destinyName, price, aeroLine);
  }


}

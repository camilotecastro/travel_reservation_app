package com.example.best_travel.api.controllers;

import com.example.best_travel.api.models.response.FlyResponse;
import com.example.best_travel.domain.infrastucture.abstractservices.IFlyService;
import com.example.best_travel.util.enums.SortTypeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/fly")
@AllArgsConstructor
@Tag(name = "Fly", description = "Fly API")
public class FlyController {

  private final IFlyService flyService;

  @Operation(summary = "Get all flies")
  @GetMapping
  public ResponseEntity<Page<FlyResponse>> getFlies(@RequestParam Integer page, @RequestParam
  Integer size, @RequestHeader(required = false) SortTypeEnum sortType) {
    if (Objects.isNull(sortType)) {
      sortType = SortTypeEnum.NONE;
    }
    var pageResponse = flyService.readAll(page, size, sortType);

    return pageResponse.isEmpty() ? ResponseEntity.noContent().build() :
        ResponseEntity.ok(pageResponse);

  }

  @Operation(summary = "Get flies with less price")
  @GetMapping(path = "/less-price")
  public ResponseEntity<Set<FlyResponse>> getBestPrice(@RequestParam BigDecimal price) {
    return ResponseEntity.ok(flyService.readLessPrice(price));
  }

  @Operation(summary = "Get flies with price between")
  @GetMapping(path = "/between-price")
  public ResponseEntity<Set<FlyResponse>> getBetweenPrince(@RequestParam BigDecimal min,
      @RequestParam BigDecimal max) {
    return ResponseEntity.ok(flyService.readBetweenPrice(min, max));
  }

  @Operation(summary = "Get flies by origin and destiny")
  @GetMapping(path = "/origin-destiny")
  public ResponseEntity<Set<FlyResponse>> getOriginDestiny(@RequestParam String origin,
      @RequestParam String destiny) {
    return ResponseEntity.ok(flyService.readByOriginDestiny(origin, destiny));
  }


}

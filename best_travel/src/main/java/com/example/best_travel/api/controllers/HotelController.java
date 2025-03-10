package com.example.best_travel.api.controllers;

import com.example.best_travel.api.models.response.HotelResponse;
import com.example.best_travel.domain.infrastucture.abstractservices.IHotelService;
import com.example.best_travel.util.enums.SortTypeEnum;
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
@RequestMapping(path = "/hotel")
@AllArgsConstructor
public class HotelController {

  private final IHotelService hotelService;


  @GetMapping
  public ResponseEntity<Page<HotelResponse>> getFlies(@RequestParam Integer page, @RequestParam
  Integer size, @RequestHeader(required = false) SortTypeEnum sortType) {
    if (Objects.isNull(sortType)) {
      sortType = SortTypeEnum.NONE;
    }
    var pageResponse = hotelService.readAll(page, size, sortType);

    return pageResponse.isEmpty() ? ResponseEntity.noContent().build() :
        ResponseEntity.ok(pageResponse);
  }

  @GetMapping(path = "/less-price")
  public ResponseEntity<Set<HotelResponse>> getBestPrice(@RequestParam BigDecimal price) {
    return ResponseEntity.ok(hotelService.readLessPrice(price));
  }

  @GetMapping(path = "/between-price")
  public ResponseEntity<Set<HotelResponse>> getBetweenPrince(@RequestParam BigDecimal min,
      @RequestParam BigDecimal max) {
    return ResponseEntity.ok(hotelService.readBetweenPrice(min, max));
  }

  @GetMapping(path = "/rating")
  public ResponseEntity<Set<HotelResponse>> getOriginDestiny(@RequestParam Integer rating
      ) {
    if (rating > 4) {rating = 4;}
    if (rating < 1) {rating = 1;}
    return ResponseEntity.ok(hotelService.readGreaterThan(rating));
  }

}

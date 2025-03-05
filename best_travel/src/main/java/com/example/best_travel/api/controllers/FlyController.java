package com.example.best_travel.api.controllers;

import com.example.best_travel.api.models.response.FlyResponse;
import com.example.best_travel.domain.infrastucture.abstractservices.IFlyService;
import com.example.best_travel.util.SortTypeEnum;
import java.util.Objects;
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
public class FlyController {

  private final IFlyService flyService;

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


}

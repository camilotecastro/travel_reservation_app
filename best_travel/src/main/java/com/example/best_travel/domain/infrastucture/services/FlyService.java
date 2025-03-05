package com.example.best_travel.domain.infrastucture.services;

import com.example.best_travel.api.models.response.FlyResponse;
import com.example.best_travel.domain.entities.Fly;
import com.example.best_travel.domain.infrastucture.abstractservices.IFlyService;
import com.example.best_travel.domain.repositories.FlyRepository;
import com.example.best_travel.util.SortTypeEnum;
import com.fasterxml.jackson.databind.util.BeanUtil;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {

  private final FlyRepository flyRepository;

  @Override
  public List<FlyResponse> readByOriginDestiny(String origin, String destiny) {
    return List.of();
  }

  @Override
  public Page<FlyResponse> readAll(Integer page, Integer size, SortTypeEnum sortType) {
    PageRequest pageRequest = null;

    switch (sortType) {
      case NONE -> pageRequest = PageRequest.of(page, size);
      case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
      case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
    }

    return this.flyRepository.findAll(pageRequest).map(this::entityToResponse);
  }

  @Override
  public List<FlyResponse> readLessPrice(BigDecimal price) {
    return List.of();
  }

  @Override
  public List<FlyResponse> readBeetwenPrice(BigDecimal min, BigDecimal max) {
    return List.of();
  }

  private FlyResponse entityToResponse(Fly fly) {
    FlyResponse response = new FlyResponse();
    BeanUtils.copyProperties(fly, response);
    return response;
  }
}

package com.example.best_travel.domain.infrastucture.services;

import com.example.best_travel.api.models.response.FlyResponse;
import com.example.best_travel.domain.entities.Fly;
import com.example.best_travel.domain.infrastucture.abstractservices.IFlyService;
import com.example.best_travel.domain.repositories.FlyRepository;
import com.example.best_travel.util.enums.SortTypeEnum;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {

  private final FlyRepository flyRepository;

  @Override
  public Set<FlyResponse> readByOriginDestiny(String origin, String destiny) {
    return this.flyRepository.selectOriginDestiny(origin, destiny)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  @Override
  public Page<FlyResponse> readAll(Integer page, Integer size, SortTypeEnum sortType) {
    PageRequest pageRequest = null;

    switch (sortType) {
      case NONE -> pageRequest = PageRequest.of(page, size);
      case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(PRICE).ascending());
      case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(PRICE).descending());
    }

    return this.flyRepository.findAll(pageRequest).map(this::entityToResponse);
  }

  @Override
  public Set<FlyResponse> readLessPrice(BigDecimal price) {
    return this.flyRepository.selectLessPrice(price)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  @Override
  public Set<FlyResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
    return this.flyRepository.selectBetweenPrice(min, max)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  private FlyResponse entityToResponse(Fly fly) {
    FlyResponse response = new FlyResponse();
    BeanUtils.copyProperties(fly, response);
    return response;
  }

}

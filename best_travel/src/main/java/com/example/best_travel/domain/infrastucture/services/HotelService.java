package com.example.best_travel.domain.infrastucture.services;

import com.example.best_travel.api.models.response.HotelResponse;
import com.example.best_travel.domain.infrastucture.abstractservices.IHotelService;
import com.example.best_travel.domain.repositories.HotelRepository;
import com.example.best_travel.util.constants.CacheConstants;
import com.example.best_travel.util.enums.SortTypeEnum;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@AllArgsConstructor
@Service
public class HotelService implements IHotelService {

  private final HotelRepository hotelRepository;

  @Override
  @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
  public Set<HotelResponse> readGreaterThan(Integer rating) {
    return this.hotelRepository.findByRatingGreaterThan(rating)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  @Override
  @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
  public Page<HotelResponse> readAll(Integer page, Integer size, SortTypeEnum sortType) {
    PageRequest pageRequest = null;

    switch (sortType) {
      case NONE -> pageRequest = PageRequest.of(page, size);
      case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(PRICE).ascending());
      case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(PRICE).descending());
    }

    return this.hotelRepository.findAll(pageRequest).map(this::entityToResponse);
  }

  @Override
  @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
  public Set<HotelResponse> readLessPrice(BigDecimal price) {
    return this.hotelRepository.findByPriceLessThan(price)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  @Override
  @Cacheable(value = CacheConstants.HOTEL_CACHE_NAME)
  public Set<HotelResponse> readBetweenPrice(BigDecimal min, BigDecimal max) {
    return this.hotelRepository.findByPriceIsBetween(min, max)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  private HotelResponse entityToResponse(Object entity) {
    HotelResponse response = new HotelResponse();
    BeanUtils.copyProperties(entity, response);
    return response;
  }

}

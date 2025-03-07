package com.example.best_travel.domain.infrastucture.abstractservices;

import com.example.best_travel.util.SortTypeEnum;
import java.math.BigDecimal;
import java.util.Set;
import org.springframework.data.domain.Page;


public interface CatalogService<R> {

  Page<R> readAll(Integer page, Integer size, SortTypeEnum sortType);

  Set<R> readLessPrice(BigDecimal price);

  Set<R> readBeetwenPrice(BigDecimal min, BigDecimal max);

  String PRICE = "price";

}

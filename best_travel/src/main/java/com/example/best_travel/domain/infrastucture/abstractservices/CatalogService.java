package com.example.best_travel.domain.infrastucture.abstractservices;

import com.example.best_travel.util.SortTypeEnum;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;


public interface CatalogService<R> {

  Page<R> readAll(Integer page, Integer size, SortTypeEnum sortType);

  List<R> readLessPrice(BigDecimal price);

  List<R> readBeetwenPrice(BigDecimal min, BigDecimal max);

  String FIELD_BY_SORT = "price";

}

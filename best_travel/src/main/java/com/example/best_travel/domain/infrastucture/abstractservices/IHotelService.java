package com.example.best_travel.domain.infrastucture.abstractservices;

import com.example.best_travel.api.models.response.HotelResponse;
import java.util.Set;

public interface IHotelService extends CatalogService<HotelResponse>{

  Set<HotelResponse> readGreaterThan(Integer rating);

}

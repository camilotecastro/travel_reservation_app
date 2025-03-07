package com.example.best_travel.domain.infrastucture.abstractservices;

import com.example.best_travel.api.models.response.FlyResponse;
import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse> {

  Set<FlyResponse> readByOriginDestiny(String origin, String destiny);

}

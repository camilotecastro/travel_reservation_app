package com.example.best_travel.domain.infrastucture.abstractservices;


public interface SimpleCrudService<RQ, RS, ID> {

  RS create(RQ request);

  RS read(ID id);

  void delete(ID id);

}

package com.example.best_travel.domain.infrastucture.abstractservices;

import com.example.best_travel.api.models.request.ReservationRequest;
import com.example.best_travel.api.models.response.ReservationResponse;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID>{

  BigDecimal findPrice(Long hotelId, Currency currency);

}

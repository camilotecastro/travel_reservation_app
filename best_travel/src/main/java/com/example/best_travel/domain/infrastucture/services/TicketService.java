package com.example.best_travel.domain.infrastucture.services;

import com.example.best_travel.api.models.request.TicketRequest;
import com.example.best_travel.api.models.response.FlyResponse;
import com.example.best_travel.api.models.response.TicketResponse;
import com.example.best_travel.domain.entities.Ticket;
import com.example.best_travel.domain.infrastucture.abstractservices.ITicketService;
import com.example.best_travel.domain.infrastucture.helpers.ApiCurrencyConnectorHelper;
import com.example.best_travel.domain.infrastucture.helpers.BlackListHelper;
import com.example.best_travel.domain.infrastucture.helpers.CustomerHelper;
import com.example.best_travel.domain.repositories.CustomerRepository;
import com.example.best_travel.domain.repositories.FlyRepository;
import com.example.best_travel.domain.repositories.TicketRepository;
import com.example.best_travel.util.BestTravelUtil;
import com.example.best_travel.util.enums.Tables;
import com.example.best_travel.util.exceptions.IdNotFoundException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TicketService implements ITicketService {

  public static final BigDecimal CHARGER_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);

  private final FlyRepository flyRepository;
  private final CustomerRepository customerRepository;
  private final TicketRepository ticketRepository;
  private final CustomerHelper customerHelper;
  private final BlackListHelper blackListHelper;
  private final ApiCurrencyConnectorHelper apiCurrencyConnectorHelper;

  @Override
  public TicketResponse create(TicketRequest request) {
    blackListHelper.isInBlackList(request.getIdClient());

    var fly = flyRepository.findById(request.getIdFly()).orElseThrow(
        () -> new IdNotFoundException(Tables.fly.name()));

    var customer = customerRepository.findById(request.getIdClient()).orElseThrow(
        () -> new IdNotFoundException(Tables.customer.name()));

    var ticketToPersist = Ticket.builder()
        .id(UUID.randomUUID())
        .fly(fly)
        .customer(customer)
        .price(fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)))
        .purchaseDate(BestTravelUtil.randomDate())
        .arrivalDate(BestTravelUtil.randomDate())
        .departureDate(BestTravelUtil.randomDate())
        .build();

    var ticketPersisted = this.ticketRepository.save(ticketToPersist);

    this.customerHelper.increment(customer.getDni(), TourService.class);

    log.info("Ticket persisted with id: {}", ticketPersisted.getId());

    return this.entityToResponse(ticketPersisted);
  }

  @Override
  public TicketResponse read(UUID uuid) {
    var ticketFromDb = this.ticketRepository.findById(uuid).orElseThrow(
        () -> new IdNotFoundException(Tables.ticket.name()));
    return this.entityToResponse(ticketFromDb);
  }

  @Override
  public TicketResponse update(UUID uuid, TicketRequest request) {
    var ticketToUpdate = ticketRepository.findById(uuid).orElseThrow(
        () -> new IdNotFoundException(Tables.ticket.name()));
    var fly = flyRepository.findById(request.getIdFly()).orElseThrow(
        () -> new IdNotFoundException(Tables.fly.name()));

    ticketToUpdate.setFly(fly);
    ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE)));
    ticketToUpdate.setPurchaseDate(BestTravelUtil.randomDate());
    ticketToUpdate.setArrivalDate(BestTravelUtil.randomDate());

    var ticketPersisted = this.ticketRepository.save(ticketToUpdate);

    log.info("Ticket updated with id: {}", ticketPersisted.getId());
    return entityToResponse(ticketPersisted);
  }

  @Override
  public void delete(UUID uuid) {
    var ticketToDelete = this.ticketRepository.findById(uuid).orElseThrow(
        () -> new IdNotFoundException(Tables.ticket.name()));
    this.ticketRepository.delete(ticketToDelete);
  }

  @Override
  public BigDecimal findPrice(Long flyId, Currency currency) {
    var fly = this.flyRepository.findById(flyId).orElseThrow(
        () -> new IdNotFoundException(Tables.fly.name()));

    var priceInDollars = fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE));

    if (currency.equals(Currency.getInstance("USD"))) {
      return priceInDollars;
    }

    var currencyDTO = apiCurrencyConnectorHelper.getCurrency(currency);
    log.info("Currency: {}", currencyDTO);

    return priceInDollars.multiply(currencyDTO.getRates().get(currency));

  }

  private TicketResponse entityToResponse(Ticket ticket) {
    var ticketResponse = new TicketResponse();
    BeanUtils.copyProperties(ticket, ticketResponse);
    var flyResponse = new FlyResponse();
    BeanUtils.copyProperties(ticket.getFly(), flyResponse);
    ticketResponse.setFlyResponse(flyResponse);
    return ticketResponse;
  }
}

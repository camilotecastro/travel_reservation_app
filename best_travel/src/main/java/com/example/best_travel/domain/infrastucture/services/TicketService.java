package com.example.best_travel.domain.infrastucture.services;

import com.example.best_travel.api.models.request.TicketRequest;
import com.example.best_travel.api.models.response.FlyResponse;
import com.example.best_travel.api.models.response.TicketResponse;
import com.example.best_travel.domain.entities.Ticket;
import com.example.best_travel.domain.infrastucture.abstractservices.ITicketService;
import com.example.best_travel.domain.repositories.CustomerRepository;
import com.example.best_travel.domain.repositories.FlyRepository;
import com.example.best_travel.domain.repositories.TicketRepository;
import com.example.best_travel.util.BestTravelUtil;
import java.math.BigDecimal;
import java.time.LocalDate;
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

  private static final BigDecimal CHARGER_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);

  private final FlyRepository flyRepository;
  private final CustomerRepository customerRepository;
  private final TicketRepository ticketRepository;

  @Override
  public TicketResponse create(TicketRequest request) {
    var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
    var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

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

    log.info("Ticket persisted with id: {}", ticketPersisted.getId());

    return this.entityToResponse(ticketPersisted);
  }

  @Override
  public TicketResponse read(UUID uuid) {
    var ticketFromDb = this.ticketRepository.findById(uuid).orElseThrow();
    return this.entityToResponse(ticketFromDb);
  }

  @Override
  public TicketResponse update(UUID uuid, TicketRequest request) {
    var ticketToUpdate = ticketRepository.findById(uuid).orElseThrow();
    var fly = flyRepository.findById(request.getIdFly()).orElseThrow();

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
    var ticketToDelete = this.ticketRepository.findById(uuid).orElseThrow();
    this.ticketRepository.delete(ticketToDelete);
  }

  @Override
  public BigDecimal findPrice(Long flyId) {
    var fly = this.flyRepository.findById(flyId).orElseThrow();
    return fly.getPrice().add(fly.getPrice().multiply(CHARGER_PRICE_PERCENTAGE));
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

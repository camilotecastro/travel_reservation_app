package com.example.best_travel;

import com.example.best_travel.domain.repositories.CustomerRepository;
import com.example.best_travel.domain.repositories.FlyRepository;
import com.example.best_travel.domain.repositories.HotelRepository;
import com.example.best_travel.domain.repositories.ReservationRepository;
import com.example.best_travel.domain.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BestTravelApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(BestTravelApplication.class);

	private final HotelRepository hotelRepository;
	private final FlyRepository flyRepository;
	private final TicketRepository ticketRepository;
	private final ReservationRepository reservationRepository;
	private final CustomerRepository customerRepository;

	public BestTravelApplication(HotelRepository hotelRepository, FlyRepository flyRepository,
      TicketRepository ticketRepository, ReservationRepository reservationRepository,
			CustomerRepository customerRepository) {
    this.hotelRepository = hotelRepository;
    this.flyRepository = flyRepository;
    this.ticketRepository = ticketRepository;
    this.reservationRepository = reservationRepository;
		this.customerRepository = customerRepository;
	}

  public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) {
/*		var fly = flyRepository.findById(15L).get();
		var hotel = hotelRepository.findById(7L).get();
		var ticket = ticketRepository.findById(UUID.fromString("12345678-1234-5678-2236-567812345678")).get();
		var reservation = reservationRepository.findById(UUID.fromString("52345678-1234-5678-1234-567812345678")).get();
		var customer = customerRepository.findById("VIKI771012HMCRG093").get();

		log.info("Hotel: {}", hotel);
		log.info("Fly: {}", fly);

		log.info("Ticket: {}", ticket);
		log.info("Reservation: {}", reservation);
		log.info("Customer: {}", customer);*/

/*		log.info("SELECT LESS PRICE------------------");
		this.flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(System.out::println);
		log.info("SELECT BETWEEN PRICE------------------");
		this.flyRepository.selectBetweenPrice(BigDecimal.valueOf(10), BigDecimal.valueOf(15))
				.forEach(System.out::println);
		log.info("SELECT ORIGIN DESTINY------------------");
		this.flyRepository.selectOriginDestiny("Grecia", "Mexico").forEach(System.out::println);


		log.info("SELECT FLY -----------------");
		var fly = flyRepository.findById(1L).get();
		log.info("Fly: {}", fly);*/

		log.info("SELECT TICKET -----------------");
		var fly2Opt = flyRepository.selectByTicketId(UUID.fromString("12345678-1234-5678-2236-567812345678"));

		if (fly2Opt.isPresent()) {
			var fly2 = fly2Opt.get();
			log.info("FLY----------------> TICKETS: {}", fly2);
			fly2.getTickets().forEach(System.out::println);
		} else {
			log.warn("No se encontró un vuelo con ese ticket ID");
		}

		log.info("SELECT LESS THAN! -----------------");
		hotelRepository.findByPriceLessThan(BigDecimal.valueOf(22.99)).forEach(System.out::println);


		log.info("SELECT Between -----------------");
		hotelRepository.findByPriceIsBetween(BigDecimal.valueOf(10), BigDecimal.valueOf(20))
				.forEach(System.out::println);

		log.info("SELECT GREATER THAN-----------------");
		hotelRepository.findByRatingGreaterThan(4).forEach(System.out::println);

		log.info("SELECT JOIN!-----------------");
		var hotel = hotelRepository.findByReservationId(UUID.fromString("52345678-1234-5678-1234-567812345678")).get();
		log.info("Hotel: {}", hotel);



	}

}

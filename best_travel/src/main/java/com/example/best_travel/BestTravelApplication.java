package com.example.best_travel;

import com.example.best_travel.domain.repositories.CustomerRepository;
import com.example.best_travel.domain.repositories.FlyRepository;
import com.example.best_travel.domain.repositories.HotelRepository;
import com.example.best_travel.domain.repositories.ReservationRepository;
import com.example.best_travel.domain.repositories.TicketRepository;
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

	@Override
	public void run(String... args) {
		var fly = flyRepository.findById(15L).get();
		var hotel = hotelRepository.findById(7L).get();
		var ticket = ticketRepository.findById(UUID.fromString("12345678-1234-5678-2236-567812345678")).get();
		var reservation = reservationRepository.findById(UUID.fromString("52345678-1234-5678-1234-567812345678")).get();
		var customer = customerRepository.findById("VIKI771012HMCRG093").get();

		log.info("Hotel: {}", hotel);
		log.info("Fly: {}", fly);

		log.info("Ticket: {}", ticket);
		log.info("Reservation: {}", reservation);
		log.info("Customer: {}", customer);


	}

}

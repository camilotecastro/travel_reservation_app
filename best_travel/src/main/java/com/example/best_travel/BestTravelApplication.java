package com.example.best_travel;

import com.example.best_travel.domain.repositories.FlyRepository;
import com.example.best_travel.domain.repositories.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BestTravelApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(BestTravelApplication.class);

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private FlyRepository flyRepository;

	public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}

	@Override
	public void run(String... args) {
		var fly = flyRepository.findById(15L).get();
		var hotel = hotelRepository.findById(7L).get();

		log.info("Hotel: {}", hotel);
		log.info("Fly: {}", fly);

		log.info("Hotel 2: {}", hotel);


	}

}

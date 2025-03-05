package com.example.best_travel.util;

import java.time.LocalDate;
import java.util.Random;

public class BestTravelUtil {

  private static final Random RANDOM = new Random();

  public static LocalDate randomDate() {
    var randomDay = RANDOM.nextInt(28) + 1;
    var randomMonth = RANDOM.nextInt(12) + 1;
    var randomYear = RANDOM.nextInt(10) + 2021;
    return LocalDate.of(randomYear, randomMonth, randomDay);
  }

}

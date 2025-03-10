package com.example.best_travel.domain.infrastucture.helpers;

import com.example.best_travel.util.exceptions.ForBiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {

  public void isInBlackList(String customerId) {
    if (customerId.equals("GOTW771012HMRGR087")) {
      throw new ForBiddenCustomerException();
    }
  }

}

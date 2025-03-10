package com.example.best_travel.util.exceptions;

public class ForBiddenCustomerException extends RuntimeException {

  public ForBiddenCustomerException() {
    super("This customer is blocked");
  }

}

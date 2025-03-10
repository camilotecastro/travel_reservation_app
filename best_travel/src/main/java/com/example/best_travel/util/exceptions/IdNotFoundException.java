package com.example.best_travel.util.exceptions;

public class IdNotFoundException extends RuntimeException {

  private static final String ERROR_MESSAGE = "Record not exist in %s";

  public IdNotFoundException(String tableName) {
    super(String.format(ERROR_MESSAGE, tableName));
  }

}

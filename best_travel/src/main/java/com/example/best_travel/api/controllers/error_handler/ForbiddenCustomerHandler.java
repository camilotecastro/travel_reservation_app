package com.example.best_travel.api.controllers.error_handler;


import com.example.best_travel.api.models.response.BaseErrorResponse;
import com.example.best_travel.api.models.response.ErrorResponse;
import com.example.best_travel.util.exceptions.ForBiddenCustomerException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenCustomerHandler {

  @ExceptionHandler(ForBiddenCustomerException.class)
  public BaseErrorResponse handleBadRequest(ForBiddenCustomerException exception) {
    return ErrorResponse.builder()
        .error(exception.getMessage())
        .status(HttpStatus.FORBIDDEN.name())
        .code(HttpStatus.FORBIDDEN.value())
        .timeStamp(LocalDateTime.now())
        .build();
  }

}

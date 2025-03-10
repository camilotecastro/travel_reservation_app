package com.example.best_travel.api.controllers.error_handler;

import com.example.best_travel.api.models.response.BaseErrorResponse;
import com.example.best_travel.api.models.response.ErrorResponse;
import com.example.best_travel.api.models.response.ErrorsResponse;
import com.example.best_travel.util.exceptions.IdNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

  @ExceptionHandler(IdNotFoundException.class)
  public BaseErrorResponse handleBadRequest(IdNotFoundException exception) {
    return ErrorResponse.builder()
        .error(exception.getMessage())
        .status(HttpStatus.BAD_REQUEST.name())
        .code(HttpStatus.BAD_REQUEST.value())
        .timeStamp(LocalDateTime.now())
        .build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public BaseErrorResponse handleIdNotFound(MethodArgumentNotValidException exception) {
    var errors = new ArrayList<String>();
    exception.getAllErrors()
        .forEach(error -> errors.add(error.getDefaultMessage()));

    return ErrorsResponse.builder()
        .errors(errors)
        .status(HttpStatus.BAD_REQUEST.name())
        .code(HttpStatus.BAD_REQUEST.value())
        .timeStamp(LocalDateTime.now())
        .build();
  }

}

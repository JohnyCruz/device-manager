package com.johny.challenge.manager.util;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse notFound(NoSuchElementException exception) {
        return ErrorResponse.create(exception, HttpStatus.NOT_FOUND, exception.getMessage());
    }

}

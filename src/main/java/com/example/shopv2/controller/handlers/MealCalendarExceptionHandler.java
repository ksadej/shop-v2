package com.example.shopv2.controller.handlers;

import com.example.shopv2.controller.handlers.dtos.ErrorMessage;
import com.example.shopv2.exceptions.MealCalendarException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MealCalendarExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorMessage mealCalendarExceptionHandler(MealCalendarException mealCalendarException){
        return ErrorMessage
                .builder()
                .errorCode(mealCalendarException.getErrorCode())
                .errorDescription(mealCalendarException.getMessage())
                .build();
    }
}

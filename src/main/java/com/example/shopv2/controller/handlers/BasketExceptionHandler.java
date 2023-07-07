package com.example.shopv2.controller.handlers;

import com.example.shopv2.controller.handlers.dtos.ErrorMessage;
import com.example.shopv2.exceptions.BasketException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BasketExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorMessage basketExceptionHandler(BasketException basketException){
        return ErrorMessage.builder()
                .errorCode(basketException.getErrorCode())
                .errorDescription(basketException.getMessage())
                .build();
    }
}

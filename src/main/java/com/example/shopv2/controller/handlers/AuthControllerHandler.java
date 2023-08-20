package com.example.shopv2.controller.handlers;

import com.example.shopv2.controller.handlers.dtos.ErrorMessage;
import com.example.shopv2.exceptions.InvalidUsernameOrPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage userNameOrPasswordHandler(InvalidUsernameOrPasswordException e){
        return ErrorMessage
                .builder()
                .errorCode(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .errorDescription(e.getMessage())
                .build();
    }
}

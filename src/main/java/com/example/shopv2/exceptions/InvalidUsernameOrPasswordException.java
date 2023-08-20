package com.example.shopv2.exceptions;

import com.example.shopv2.validator.enums.AuthenticationEnum;

public class InvalidUsernameOrPasswordException extends RuntimeException{

    public InvalidUsernameOrPasswordException() {
        super(AuthenticationEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());
    }
}

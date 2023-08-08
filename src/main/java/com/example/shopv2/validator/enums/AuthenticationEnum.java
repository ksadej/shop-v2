package com.example.shopv2.validator.enums;

public enum AuthenticationEnum {

    USER_NOT_FOUND("User not found"),
    USER_EXISTS("User exists");


    private final String message;

    AuthenticationEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

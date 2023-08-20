package com.example.shopv2.validator.enums;

public enum AuthenticationEnum {

    USER_NOT_FOUND("User not found"),
    USER_EXISTS("User exists"),
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password");


    private final String message;

    AuthenticationEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

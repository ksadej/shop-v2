package com.example.shopv2.exceptions;

public class MealCalendarException extends RuntimeException{

    private String errorCode;

    public MealCalendarException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

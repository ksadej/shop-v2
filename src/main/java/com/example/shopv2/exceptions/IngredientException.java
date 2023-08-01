package com.example.shopv2.exceptions;

public class IngredientException extends RuntimeException{

    private String errorCode;

    public IngredientException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

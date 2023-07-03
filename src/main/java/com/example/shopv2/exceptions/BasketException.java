package com.example.shopv2.exceptions;

public class BasketException  extends RuntimeException{

    private String errorCode;

    public BasketException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

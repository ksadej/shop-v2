package com.example.shopv2.exceptions;

public class MissingFilterException extends RuntimeException{
    private String errorCode;

    public MissingFilterException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

package com.example.shopv2.exceptions;

public class FilterException extends RuntimeException{
    private String errorCode;

    public FilterException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

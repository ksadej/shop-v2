package com.example.shopv2.model.enums;

public enum OderStatus {

    NEW("New"),
    PAID("Paid"),
    PROCESSING("Processing"),
    WAITING_FOR_DELIVERY("In delivery"),
    COMPLETED("Completed"),
    CANCELED("Canceled"),
    REFUND("Refund");

    private final String message;

    OderStatus(String message) {
        this.message = message;
    }
}

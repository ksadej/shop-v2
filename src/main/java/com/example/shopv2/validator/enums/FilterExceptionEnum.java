package com.example.shopv2.validator.enums;

public enum FilterExceptionEnum {

    MISSING_BASKET_FILTER_KEY("Missing filter key for Basket"),
    MISSING_MEALCALENDAR_FILTER_KEY("Missing filter key fo Meal Calendar");

    private final String message;

    FilterExceptionEnum(String message) {
        this.message = message;
    }

    public String getMessage(String missingKey) {
        return this.message + missingKey;
    }
}

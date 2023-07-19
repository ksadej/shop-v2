package com.example.shopv2.validator.enums;

public enum MealCalendarEnum {

    ID_RECIPES("Invalid idRecipes value"),
    TIME_MEAL("Invalid time value"),
    DAY("Invalid day value"),
    ID("Invalid ID value for delete"),
    DATA_MEAL("Invalid DATE MEAL"),
    MISSING_FILTER_KEY("Missing filter key");

    private final String message;

    MealCalendarEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

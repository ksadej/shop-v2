package com.example.shopv2.validator.enums;

public enum MealCalendarEnum {

    ID_RECIPES("Invalid idRecipes value"),
    MEAL_DATE("Invalid meal date value");

    private final String message;

    MealCalendarEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

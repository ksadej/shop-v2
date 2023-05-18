package com.example.shopv2.validator.enums;

public enum MealCalendarEnum {

    ID_RECIPES("Invalid idRecipes value"),
    MEAL_DATE("Invalid meal date value"),
    TIME("Invalid time value"),
    DAYS("Invalid day value"),
    ID("Invalid ID value for delete");

    private final String message;

    MealCalendarEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

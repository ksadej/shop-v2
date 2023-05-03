package com.example.shopv2.validator.enums;

public enum RecipesValidatorEnum {

    CAN_NOT_BE_NULL("Type can't be null"),
    TO_SHORT("Type can not be that short");
    private final String message;

    RecipesValidatorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

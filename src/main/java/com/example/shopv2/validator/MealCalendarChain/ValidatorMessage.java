package com.example.shopv2.validator.MealCalendarChain;

public class ValidatorMessage {

    private StringBuilder message;
    private StringBuilder code;

    public String getMessage() {
        return message.toString();
    }

    public void setMessage(String message) {
        this.message.append(message);
    }

    public String getCode() {
        return code.toString();
    }

    public void setCode(String code) {
        this.code.append(code);
    }
}

package com.example.shopv2.validator.MealCalendarChain;

import com.example.shopv2.controller.dto.MealCalendarRequest;

public abstract class Validator {
    protected Validator next;

    public abstract void handler(MealCalendarRequest mealCalendarRequest);

    public void setNext(Validator next) {
        this.next = next;
    }
}

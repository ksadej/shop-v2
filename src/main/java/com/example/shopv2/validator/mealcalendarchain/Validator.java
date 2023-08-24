package com.example.shopv2.validator.mealcalendarchain;

import com.example.shopv2.service.dto.MealCalendarDTO;

public abstract class Validator {
    protected Validator next;

    public abstract void handler(MealCalendarDTO mealCalendarDTO);

    public void setNext(Validator next) {
        this.next = next;
    }
}

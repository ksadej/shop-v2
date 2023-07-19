package com.example.shopv2.validator.MealCalendarChain;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.util.Objects;

public class DayValidator extends Validator {

    @Override
    public void handler(MealCalendarRequest mealCalendarRequest) {
        if(Objects.isNull(mealCalendarRequest.getDay()) || mealCalendarRequest.getDay().toString().equals("")){
            throw new MealCalendarException(MealCalendarEnum.DAY.getMessage(), "Error code: 5");
        }
        next.handler(mealCalendarRequest);

    }
}

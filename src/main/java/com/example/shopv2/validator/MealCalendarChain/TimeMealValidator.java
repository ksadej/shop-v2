package com.example.shopv2.validator.MealCalendarChain;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.util.Objects;

public class TimeMealValidator extends Validator {

    @Override
    public void handler(MealCalendarRequest mealCalendarRequest) {
        if(Objects.isNull(mealCalendarRequest.getTime())) {
            throw new MealCalendarException(MealCalendarEnum.TIME_MEAL.getMessage(), "Error code: 3");
        }
        next.handler(mealCalendarRequest);

    }
}

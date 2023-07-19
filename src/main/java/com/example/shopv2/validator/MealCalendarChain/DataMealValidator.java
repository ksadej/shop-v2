package com.example.shopv2.validator.MealCalendarChain;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.time.OffsetDateTime;
import java.util.Objects;

public class DataMealValidator extends Validator {

    private Validator next;

    @Override
    public void handler(MealCalendarRequest mealCalendarRequest) {
        if(Objects.isNull(mealCalendarRequest.getDataMeal()) || OffsetDateTime.now().isAfter(mealCalendarRequest.getDataMeal())){
            throw new MealCalendarException(MealCalendarEnum.DATA_MEAL.getMessage(),  "Error code: 4");
        } else if (next != null) {
            next.handler(mealCalendarRequest);
        }
    }
}

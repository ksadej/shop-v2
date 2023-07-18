package com.example.shopv2.validator.MealCalendarChain;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.validator.Validator;
import com.example.shopv2.validator.ValidatorMessage;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.util.Objects;

public class TimeMealValidator implements Validator {

    private Validator next = new DayValidator();

    @Override
    public ValidatorMessage valid(MealCalendarRequest mealCalendarRequest, ValidatorMessage validatorMessage) {
        if(Objects.isNull(mealCalendarRequest.getTime()) || mealCalendarRequest.getTime().toString().equals("")) {
            validatorMessage.setMessage(MealCalendarEnum.TIME_MEAL.getMessage());
            validatorMessage.setCode("Error code: 3");
        }
        return next.valid(mealCalendarRequest, validatorMessage);
    }
}

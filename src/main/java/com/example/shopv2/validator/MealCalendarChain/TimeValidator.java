package com.example.shopv2.validator.MealCalendarChain;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.util.Objects;

public class TimeValidator implements Validator{

    private Validator next = new IdRecipesValidator();

    @Override
    public ValidatorMessage valid(MealCalendarRequest mealCalendarRequest, ValidatorMessage validatorMessage) {
        if(mealCalendarRequest.getTime().toString().equals("")) {
            validatorMessage.setMessage(MealCalendarEnum.TIME.getMessage());
            validatorMessage.setCode("Error code: 3");
        }

        return next.valid(mealCalendarRequest, validatorMessage);
    }
}

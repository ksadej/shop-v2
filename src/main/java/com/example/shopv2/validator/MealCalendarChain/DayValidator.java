package com.example.shopv2.validator.MealCalendarChain;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.validator.Validator;
import com.example.shopv2.validator.ValidatorMessage;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.util.Objects;

public class DayValidator implements Validator {

    private Validator next = new IdRecipesValidator();

    @Override
    public ValidatorMessage valid(MealCalendarRequest mealCalendarRequest, ValidatorMessage validatorMessage) {
        if(Objects.isNull(mealCalendarRequest.getDay()) || mealCalendarRequest.getDay().toString().equals("")){
            validatorMessage.setMessage(MealCalendarEnum.DAY.getMessage());
            validatorMessage.setCode("Error code: 5");
        }

        return next.valid(mealCalendarRequest, validatorMessage);
    }
}

package com.example.shopv2.validator.MealCalendarChain;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.validator.Validator;
import com.example.shopv2.validator.ValidatorMessage;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.time.OffsetDateTime;
import java.util.Objects;

public class DataMealValidator implements Validator {

    private Validator next = new TimeMealValidator();


    @Override
    public ValidatorMessage valid(MealCalendarRequest mealCalendarRequest, ValidatorMessage validatorMessage) {
        if(Objects.isNull(mealCalendarRequest.getDataMeal()) || OffsetDateTime.now().isAfter(mealCalendarRequest.getDataMeal())){
            validatorMessage.setMessage(MealCalendarEnum.DATA_MEAL.getMessage());
            validatorMessage.setCode("Error code: 4");
        }
        return next.valid(mealCalendarRequest, validatorMessage);
    }
}

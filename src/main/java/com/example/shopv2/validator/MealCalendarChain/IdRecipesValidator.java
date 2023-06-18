package com.example.shopv2.validator.MealCalendarChain;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.util.Objects;

public class IdRecipesValidator implements Validator{
    @Override
    public ValidatorMessage valid(MealCalendarRequest mealCalendarRequest, ValidatorMessage validatorMessage) {
        if(mealCalendarRequest.getIdRecipes() <= 0){
            validatorMessage.setMessage(MealCalendarEnum.ID_RECIPES.getMessage());
            validatorMessage.setCode("Error code: 2");
        }
        return validatorMessage;
    }
}

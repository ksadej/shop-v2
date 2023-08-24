package com.example.shopv2.validator.mealcalendarchain;

import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.service.dto.MealCalendarDTO;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.util.Objects;

public class IdRecipesValidator extends Validator {

    @Override
    public void handler(MealCalendarDTO mealCalendarDTO) {
        if(Objects.isNull(mealCalendarDTO.getIdRecipes()) || mealCalendarDTO.getIdRecipes() <= 0){
            throw new MealCalendarException(MealCalendarEnum.ID_RECIPES.getMessage(),  "Error code: 2");
        }
        next.handler(mealCalendarDTO);

    }
}

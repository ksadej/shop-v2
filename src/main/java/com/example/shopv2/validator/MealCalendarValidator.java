package com.example.shopv2.validator;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.model.enums.MealTime;
import com.example.shopv2.validator.enums.MealCalendarEnum;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Configuration
public class MealCalendarValidator {

    public void saveMealCalendarValidator(MealCalendarRequest mealCalendarRequest){
        if( mealCalendarRequest.getTime().toString().equals("")) {
            throw new MealCalendarException("Empty mealCalendarRequest object");
        }

        if(Objects.isNull(mealCalendarRequest.getIdRecipes()) || mealCalendarRequest.getIdRecipes() <= 0){
            throw new MealCalendarException(MealCalendarEnum.ID_RECIPES.getMessage());
        }

        if(Objects.isNull(mealCalendarRequest.getDataMeal()) || OffsetDateTime.now().isAfter(mealCalendarRequest.getDataMeal())){
            throw new MealCalendarException(MealCalendarEnum.MEAL_DATE.getMessage());
        }

    }


}

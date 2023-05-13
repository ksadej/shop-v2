package com.example.shopv2.validator;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.validator.enums.MealCalendarEnum;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.util.Objects;

@Component
public class MealCalendarValidator {

    public void saveMealCalendarValidator(MealCalendarRequest mealCalendarRequest){


        if(mealCalendarRequest == null){
            throw new MealCalendarException("Object is null");
        }

        if(Objects.isNull(mealCalendarRequest.getIdRecipes()) || mealCalendarRequest.getIdRecipes() <= 0){
            throw new MealCalendarException(MealCalendarEnum.ID_RECIPES.getMessage());
        }

        if(Objects.isNull(mealCalendarRequest.getTime()) || mealCalendarRequest.getTime().toString().equals("")) {
            throw new MealCalendarException(MealCalendarEnum.TIME.getMessage());
        }

        if(Objects.isNull(mealCalendarRequest.getDataMeal()) || OffsetDateTime.now().isAfter(mealCalendarRequest.getDataMeal())){
            throw new MealCalendarException(MealCalendarEnum.MEAL_DATE.getMessage());
        }

        if(Objects.isNull(mealCalendarRequest.getDay())){
            throw new MealCalendarException(MealCalendarEnum.DAYS.getMessage());
        }

    }


}

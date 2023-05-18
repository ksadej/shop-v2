package com.example.shopv2.validator;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.repository.MealCalendarRepository;
import com.example.shopv2.validator.enums.MealCalendarEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.OffsetDateTime;
import java.util.Objects;

@Component
public class MealCalendarValidator {

    private final MealCalendarRepository mealCalendarRepository;

    public MealCalendarValidator(MealCalendarRepository mealCalendarRepository) {
        this.mealCalendarRepository = mealCalendarRepository;
    }

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

    public void getByDayAndTimeValidator(MealCalendarResponse mealCalendarResponse){

        if(Objects.isNull(mealCalendarResponse.getDay()) || mealCalendarResponse.getDay().toString().equals("") ){
            throw new MealCalendarException(MealCalendarEnum.DAYS.getMessage());
        }

        if(Objects.isNull(mealCalendarResponse.getTime()) || mealCalendarResponse.getTime().toString().equals("")) {
            throw new MealCalendarException(MealCalendarEnum.TIME.getMessage());
        }
    }

    public void deleteMealCalendarValidator(Long id){

        if((id == null) || (id <=0) || (!mealCalendarRepository.existsById(id))){
            throw new MealCalendarException(MealCalendarEnum.ID.getMessage());
        }
    }
}

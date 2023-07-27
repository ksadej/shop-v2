package com.example.shopv2.validator;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.repository.MealCalendarRepository;
import com.example.shopv2.validator.mealcalendarchain.*;
import com.example.shopv2.validator.enums.FilterParametersEnum;
import com.example.shopv2.validator.enums.MealCalendarEnum;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class MealCalendarValidator {

    private final MealCalendarRepository mealCalendarRepository;


    public MealCalendarValidator(MealCalendarRepository mealCalendarRepository) {
        this.mealCalendarRepository = mealCalendarRepository;
    }

    public void saveMealCalendarValidator(MealCalendarRequest mealCalendarRequest){
        Validator idRecipes = new IdRecipesValidator();
        Validator dayValidator = new DayValidator();
        Validator timeMeal = new TimeMealValidator();
        Validator dataMeal = new DataMealValidator();
        idRecipes.setNext(dayValidator);
        dayValidator.setNext(timeMeal);
        timeMeal.setNext(dataMeal);

        idRecipes.handler(mealCalendarRequest);
    }

    public void getByDayAndTimeValidator(MealCalendarResponse mealCalendarResponse){

        if(Objects.isNull(mealCalendarResponse.getDay()) || mealCalendarResponse.getDay().toString().equals("") ){
            throw new MealCalendarException(MealCalendarEnum.DAY.getMessage(), "Error code: 6");
        }

        if(Objects.isNull(mealCalendarResponse.getTime()) || mealCalendarResponse.getTime().toString().equals("")) {
            throw new MealCalendarException(MealCalendarEnum.TIME_MEAL.getMessage(),  "Error code: 7");
        }
    }

    public void deleteMealCalendarValidator(Long id){

        if((id == null) || (id <=0) || (!mealCalendarRepository.existsById(id))){
            throw new MealCalendarException(MealCalendarEnum.ID.getMessage(),  "Error code: 8");
        }
    }

}

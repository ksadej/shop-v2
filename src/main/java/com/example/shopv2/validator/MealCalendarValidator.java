package com.example.shopv2.validator;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.repository.MealCalendarRepository;
import com.example.shopv2.validator.MealCalendarChain.DataMealValidator;
import com.example.shopv2.validator.MealCalendarChain.DayMealValidator;
import com.example.shopv2.validator.MealCalendarChain.IdRecipesValidator;
import com.example.shopv2.validator.enums.MealCalendarEnum;
import org.springframework.stereotype.Component;

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
            throw new MealCalendarException("Object is null", "Error code: 1");
        }

        if(Objects.isNull(mealCalendarRequest.getIdRecipes()) || mealCalendarRequest.getIdRecipes() <= 0){
            throw new MealCalendarException(MealCalendarEnum.ID_RECIPES.getMessage(),  "Error code: 2");
        }

        if(Objects.isNull(mealCalendarRequest.getTime()) || mealCalendarRequest.getTime().toString().equals("")) {
            throw new MealCalendarException(MealCalendarEnum.TIME_MEAL.getMessage(), "Error code: 3");
        }

        if(Objects.isNull(mealCalendarRequest.getDataMeal()) || OffsetDateTime.now().isAfter(mealCalendarRequest.getDataMeal())){
            throw new MealCalendarException(MealCalendarEnum.DATA_MEAL.getMessage(),  "Error code: 4");
        }

        if(Objects.isNull(mealCalendarRequest.getDay())){
            throw new MealCalendarException(MealCalendarEnum.DAYS.getMessage(),  "Error code: 5");
        }
    }

    public void getByDayAndTimeValidator(MealCalendarResponse mealCalendarResponse){

        if(Objects.isNull(mealCalendarResponse.getDay()) || mealCalendarResponse.getDay().toString().equals("") ){
            throw new MealCalendarException(MealCalendarEnum.DAYS.getMessage(), "Error code: 6");
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


    private Validator validator = new IdRecipesValidator();

    public void saveMealCalendarValidator2(MealCalendarRequest mealCalendarRequest){

        System.out.println("VALIDACJA START");
        ValidatorMessage validatorMessage = validator.valid(mealCalendarRequest, new ValidatorMessage());
        if(validatorMessage.getMessage().isEmpty()){
            return;
        }
        throw new MealCalendarException(validatorMessage.getMessage(), validatorMessage.getCode());
    }

}

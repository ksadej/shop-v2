package com.example.shopv2.validator;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.repository.MealCalendarRepository;
import com.example.shopv2.validator.MealCalendarChain.IdRecipesValidator;
import com.example.shopv2.validator.enums.FilterParametersEnum;
import com.example.shopv2.validator.enums.MealCalendarEnum;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Map;
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

        if(Objects.isNull(mealCalendarRequest.getDay()) || mealCalendarRequest.getDay().toString().equals("")){
            throw new MealCalendarException(MealCalendarEnum.DAY.getMessage(),  "Error code: 5");
        }
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

    public void  filterMealsBetweenDateValidator(Map<String, String> filters){

        //check if year exist, month missing
        if (filters.containsKey(FilterParametersEnum.MONTH.getKey())
                && !filters.containsKey(FilterParametersEnum.YEAR.getKey())){
            throw new MealCalendarException(FilterParametersEnum.MONTH.getKey(), "Error code: invalid date: no month!");
        }

        //check if toDate exist, fromDate missing
        if (filters.containsKey(FilterParametersEnum.TO_DATE.getKey())
                && !filters.containsKey(FilterParametersEnum.FROM_DATE.getKey())) {

            throw new MealCalendarException(FilterParametersEnum.FROM_DATE.getKey(), "Error code: invalid date: no from date!");
        }

        //check if fromDate exist, toDate missing
        if (filters.containsKey(FilterParametersEnum.FROM_DATE.getKey())
                && !filters.containsKey(FilterParametersEnum.TO_DATE.getKey())) {

            throw new MealCalendarException(FilterParametersEnum.TO_DATE.getKey(), "Error code: invalid date: no to date!");
        }
    }


    private Validator validator = new IdRecipesValidator();

    public void saveMealCalendarValidator2(MealCalendarRequest mealCalendarRequest){

        System.out.println("VALIDACJA START 1");
        ValidatorMessage validatorMessage = validator.valid(mealCalendarRequest, new ValidatorMessage());
        System.out.println("VALIDACJA START 2");
        if(validatorMessage.getMessage().isEmpty()){
            return;
        }
        throw new MealCalendarException(validatorMessage.getMessage(), validatorMessage.getCode());
    }

}

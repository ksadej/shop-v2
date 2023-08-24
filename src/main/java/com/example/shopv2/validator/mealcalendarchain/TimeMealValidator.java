package com.example.shopv2.validator.mealcalendarchain;

import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.service.dto.MealCalendarDTO;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.util.Objects;

public class TimeMealValidator extends Validator {

    @Override
    public void handler(MealCalendarDTO mealCalendarDTO) {
        if(Objects.isNull(mealCalendarDTO.getTime())) {
            throw new MealCalendarException(MealCalendarEnum.TIME_MEAL.getMessage(), "Error code: 3");
        }
        next.handler(mealCalendarDTO);

    }
}

package com.example.shopv2.validator.mealcalendarchain;

import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.service.dto.MealCalendarDTO;
import com.example.shopv2.validator.enums.MealCalendarEnum;

import java.util.Objects;

public class DayValidator extends Validator {

    @Override
    public void handler(MealCalendarDTO mealCalendarDTO) {
        if(Objects.isNull(mealCalendarDTO.getDay()) || mealCalendarDTO.getDay().toString().equals("")){
            throw new MealCalendarException(MealCalendarEnum.DAY.getMessage(), "Error code: 5");
        }
        next.handler(mealCalendarDTO);

    }
}

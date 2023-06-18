package com.example.shopv2.validator.MealCalendarChain;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.model.MealCalendar;

public interface Validator {
    ValidatorMessage valid(MealCalendarRequest mealCalendarRequest, ValidatorMessage validatorMessage);
}

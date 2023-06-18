package com.example.shopv2.validator;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.validator.ValidatorMessage;

public interface Validator {
    ValidatorMessage valid(MealCalendarRequest mealCalendarRequest, ValidatorMessage validatorMessage);
}

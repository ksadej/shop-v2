package com.example.shopv2.validator.ParametersValidatorFactory;

import com.example.shopv2.exceptions.FilterException;
import com.example.shopv2.validator.enums.FilterExceptionEnum;
import org.springframework.stereotype.Component;

@Component
public class MealCalendarParametersValidator extends  FilterValidator{
    @Override
    public void throwException(String missingKey, String errorCode) {
        throw new FilterException(FilterExceptionEnum.MISSING_MEALCALENDAR_FILTER_KEY.getMessage(missingKey), errorCode);
    }
}

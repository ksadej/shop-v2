package com.example.shopv2.validator.filterfactory;

import com.example.shopv2.exceptions.MissingFilterException;
import org.springframework.stereotype.Component;

@Component
public class MealCalendarParametersValidator extends  FilterValidator{
    @Override
    public void throwException(String missingKey, String errorCode) {
        throw new MissingFilterException("Missing filtered key", errorCode);
    }
}

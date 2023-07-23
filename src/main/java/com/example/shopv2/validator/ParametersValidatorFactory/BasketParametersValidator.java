package com.example.shopv2.validator.ParametersValidatorFactory;

import com.example.shopv2.exceptions.FilterException;
import com.example.shopv2.validator.enums.FilterExceptionEnum;
import org.springframework.stereotype.Component;

@Component
public class BasketParametersValidator extends FilterValidator{
    @Override
    public void throwException(String missingKey, String errorCode) {
        throw new FilterException(FilterExceptionEnum.MISSING_BASKET_FILTER_KEY.getMessage(missingKey), errorCode);
    }
}

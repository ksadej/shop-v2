package com.example.shopv2.validator.ParametersValidatorFactory;

import com.example.shopv2.exceptions.FilterException;
import com.example.shopv2.validator.enums.FilterParametersEnum;

import java.util.Map;

public abstract class FilterValidator {

    public void validateFilter(Map<String, String> filter){
        checkIfFromDateExistToDateMissing(filter, "Invalid to date key");
        checkIfToDateExistFromDateMissing(filter, "Invalid from date key");
    }

    private void checkIfToDateExistFromDateMissing(Map<String, String> filter, String errorCode) {
        if(filter.containsKey(FilterParametersEnum.TO_DATE.getKey()) && !filter.containsKey(FilterParametersEnum.FROM_DATE.getKey())){
            throw new FilterException(FilterParametersEnum.FROM_DATE.getKey(), errorCode);
        }
    }

    private void checkIfFromDateExistToDateMissing(Map<String, String> filter, String errorCode) {
        if(filter.containsKey(FilterParametersEnum.FROM_DATE.getKey()) && !filter.containsKey(FilterParametersEnum.TO_DATE.getKey())){
            throwException(FilterParametersEnum.TO_DATE.getKey(), errorCode);
        }
    }

    public abstract void throwException(String missingKey, String errorCode);

}

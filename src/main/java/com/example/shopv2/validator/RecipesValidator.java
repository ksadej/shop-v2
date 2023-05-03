package com.example.shopv2.validator;

import com.example.shopv2.exceptions.RecipesIncompleteException;
import com.example.shopv2.validator.enums.RecipesValidatorEnum;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class RecipesValidator {

    public void getRecipesByTypeValidate(String type){
        if(type.length() < 3){
            throw new RecipesIncompleteException(RecipesValidatorEnum.TO_SHORT.getMessage());
        }

        if (Objects.isNull(type) || type.length() == 0){
            throw new RecipesIncompleteException(RecipesValidatorEnum.CAN_NOT_BE_NULL.getMessage());
        }
    }
}

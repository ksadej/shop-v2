package com.example.shopv2.validator;

import com.example.shopv2.exceptions.IngredientException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class IngredientValidator {

    public void valid(Long id){
        if(Objects.isNull(id) || (id <=0)){
            throw new IngredientException("Invalid id","Error code message 0202");
        }
    }

    public void valid2(ArrayList<Long> list){
        if(list.isEmpty()){
            throw new IngredientException("Invalid id","Error code message 0202");
        }
    }
}

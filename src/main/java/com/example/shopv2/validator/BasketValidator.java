package com.example.shopv2.validator;

import com.example.shopv2.exceptions.BasketException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BasketValidator {

    public void basketDataValidator(Integer id){
        if(id <=0){
            throw new BasketException("Code 02", "Object is 0");
        }
    }
}

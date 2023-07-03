package com.example.shopv2.validator;

import com.example.shopv2.exceptions.BasketException;

import java.util.Objects;

public class BasketValidator {

    public void basketDataValidator(Integer id){
        if(Objects.isNull(id)){
            throw new BasketException("Code 01", "Object is null");
        }

        if(id == null || id <=0){
            throw new BasketException("Code 02", "Object is null or 0");
        }

    }
}

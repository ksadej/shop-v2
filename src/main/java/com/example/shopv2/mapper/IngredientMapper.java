package com.example.shopv2.mapper;

import com.example.shopv2.model.Ingredient;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredientMapper{

    public Ingredient requestToEntity(RecipesIngredientPojo recipesIngredientPojo) {
        return Ingredient
                .builder()
                .aisle(recipesIngredientPojo.getAisle())
                .amount(recipesIngredientPojo.getAmount())
                .name(recipesIngredientPojo.getName())
                .consistency(recipesIngredientPojo.getConsistency())
                .image(recipesIngredientPojo.getImage())
                .unit(recipesIngredientPojo.getUnit())
                .original(recipesIngredientPojo.getOriginal())
                .idIngredientAPI(recipesIngredientPojo.getId())
                .build();
    }
}

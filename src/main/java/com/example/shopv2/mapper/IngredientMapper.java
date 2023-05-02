package com.example.shopv2.mapper;

import com.example.shopv2.model.Ingredient;
import com.example.shopv2.pojo.RecipesIngredientPojo;

import java.util.List;

public class IngredientMapper {

    public Ingredient ingredientPojoToIngredient(List<RecipesIngredientPojo> recipesIngredientPojo, int i){
        return Ingredient
                .builder()
                .aisle(recipesIngredientPojo.stream().map(c -> c.getAisle()).toList().get(i))
                .amount(recipesIngredientPojo.stream().map(c -> c.getAmount()).toList().get(i))
                .name(recipesIngredientPojo.stream().map(c -> c.getName()).toList().get(i))
                .consistency(recipesIngredientPojo.stream().map(c -> c.getConsistency()).toList().get(i))
                .image(recipesIngredientPojo.stream().map(c -> c.getImage()).toList().get(i))
                .unit(recipesIngredientPojo.stream().map(c -> c.getUnit()).toList().get(i))
                .original(recipesIngredientPojo.stream().map(c -> c.getOriginal()).toList().get(i))
                .idIngredientAPI(recipesIngredientPojo.stream().map(c -> c.getId()).toList().get(i))
                .build();
    }
}

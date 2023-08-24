package com.example.shopv2.mapper;

import com.example.shopv2.model.Ingredient;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import com.example.shopv2.service.dto.IngredientDTO;
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

    public IngredientDTO entityToDto(Ingredient ingredient){
        return IngredientDTO
                .builder()
                .aisle(ingredient.getAisle())
                .amount(ingredient.getAmount())
                .name(ingredient.getName())
                .consistency(ingredient.getConsistency())
                .image(ingredient.getImage())
                .unit(ingredient.getUnit())
                .original(ingredient.getOriginal())
                .idIngredientAPI(ingredient.getIdIngredientAPI())
                .build();
    }
}

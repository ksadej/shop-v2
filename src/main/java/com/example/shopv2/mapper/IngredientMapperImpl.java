package com.example.shopv2.mapper;

import com.example.shopv2.model.Ingredient;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.pojo.RecipesIngredientPojo;

public interface IngredientMapperImpl {

    Object requestToEntity(RecipesIngredientPojo recipesIngredientPojo);
}

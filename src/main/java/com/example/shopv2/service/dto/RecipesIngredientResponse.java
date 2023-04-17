package com.example.shopv2.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecipesIngredientResponse {
    public int id;
    public String aisle;
    public String image;
    public String consistency;
    public String name;
    public String nameClean;
    public String original;
    public String originalName;
    public double amount;
    public String unit;
    public ArrayList<String> meta;
    public RecipesIngredientMeasures measures;
    public NutritionResponse nutrition;
    public ArrayList<RecipesIngredientResponse> extendedIngredients;

}

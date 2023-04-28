package com.example.shopv2.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecipesIngredientPojo {
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
    public RecipesIngredientMeasuresPojo measures;
    public NutritionPojo nutrition;
    public ArrayList<RecipesIngredientPojo> extendedIngredients;

}

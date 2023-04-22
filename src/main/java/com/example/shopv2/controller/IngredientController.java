package com.example.shopv2.controller;

import com.example.shopv2.model.Ingredient;
import com.example.shopv2.service.IngredientService;
import com.example.shopv2.service.dto.NutritionNutrientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredient/{id}")
    public ArrayList<NutritionNutrientResponse> getNutritionByIngredientId(@PathVariable Long id){
        return ingredientService.getNutritionByIngredientId(id);
    }

//    @GetMapping("/ingredient/all")
//    public List<Ingredient> getAll(){
//        return ingredientService.getAllIngredients();
//    }
}

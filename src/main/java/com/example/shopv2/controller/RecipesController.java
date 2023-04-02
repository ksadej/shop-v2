package com.example.shopv2.controller;

import com.example.shopv2.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipesController {

    private final RecipesService recipesService;

    @Autowired
    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @GetMapping("/api/recipes")
    public String getRecipesByIngredients(@RequestParam String ingredients){
        return recipesService.getRecipesByIngredients(ingredients);
    }

    //testy
    @GetMapping("/api/test/recipes")
    public String getRecipesByIngredients1(@RequestParam Long id){
        return recipesService.getRecipesByIngredients1(id);
    }
}

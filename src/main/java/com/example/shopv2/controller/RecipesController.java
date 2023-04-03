package com.example.shopv2.controller;

import com.example.shopv2.service.RecipesService;
import com.example.shopv2.service.dto.RecipesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipesController {

    private final RecipesService recipesService;

    @Autowired
    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }


    @GetMapping("/api/recipes")
    public List<RecipesResponse> getRecipesByIngredients1(@RequestParam Long id){
        return recipesService.getRecipesByIngredients(id);
    }
}

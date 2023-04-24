package com.example.shopv2.controller;

import com.example.shopv2.service.RecipesService;
import com.example.shopv2.service.dto.RecipesIngredientResponse;
import com.example.shopv2.service.dto.RecipesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RecipesController {

    private final RecipesService recipesService;

    @Autowired
    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }


    @GetMapping("/api/recipes")
    public List<RecipesResponse> getRecipesByIngredients(@RequestParam Long id){
        return recipesService.getRecipesByIngredients(id);
    }

    @GetMapping("/api/recipes/{type}")
    public List<RecipesResponse> getRecipesByType(@PathVariable(value = "type") String type){
        return recipesService.getRecipesByType(type);
    }



    //pobiera recepte na podstawie jej nr id
    @GetMapping("/api/recipes/v1/{id}")
    public RecipesResponse getRecipesById(@PathVariable(value = "id")Integer id){
        return recipesService.getRecipesById(id);
    }

}

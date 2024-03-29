package com.example.shopv2.controller;

import com.example.shopv2.model.Recipes;
import com.example.shopv2.service.RecipesService;
import com.example.shopv2.pojo.RecipesPojo;
import com.example.shopv2.service.dto.RecipesDTO;
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

    //pobiera przepisy na podstawie ich typu
    @GetMapping("/recipes/{type}")
    public List<RecipesPojo> getRecipesByType(@PathVariable(value = "type") String type){
        return recipesService.getRecipesByType(type);
    }

    //pobiera recepte na podstawie jej nr id
    @GetMapping("/recipes/v1/{id}")
    public RecipesPojo getRecipesById(@PathVariable(value = "id")Integer id){
        return recipesService.getRecipesById(id);
    }

    //pobiera listę recept na podstawie user z tabeli basket
    @GetMapping(path = "/recipes/user")
    public List<RecipesDTO> getAllIngredientsByCardId(){
        return recipesService.getRecipesByUserId();
    }
}

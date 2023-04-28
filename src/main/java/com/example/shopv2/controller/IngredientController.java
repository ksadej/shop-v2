package com.example.shopv2.controller;

import com.example.shopv2.service.IngredientService;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    //pobiera Listę składników z przepisu na podstawie id przepisu
    @GetMapping("/api/recipes/ingredient/{id}")
    public List<RecipesIngredientPojo> getRecipesIngredientsByRecipesId(@PathVariable(value = "id")Integer id){
        return ingredientService.getIngredientByRecipesId(id);
    }

//    @GetMapping("/ingredient/all")
//    public List<Ingredient> getAll(){
//        return ingredientService.getAllIngredients();
//    }
}

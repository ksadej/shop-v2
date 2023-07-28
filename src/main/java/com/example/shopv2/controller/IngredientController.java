package com.example.shopv2.controller;

import com.example.shopv2.model.Ingredient;
import com.example.shopv2.repository.IngredientRepository;
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
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientService ingredientService, IngredientRepository ingredientRepository) {
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
    }

    //pobiera Listę składników z przepisu na podstawie id przepisu
    @GetMapping("/ingredient/recipes/{id}")
    public List<RecipesIngredientPojo> getRecipesIngredientsByRecipesId(@PathVariable(value = "id")Integer id){
        return ingredientService.getIngredientByRecipesId(id);
    }

    //pobiera listę składników na podstawie card id
    @GetMapping("/ingredient/card/{id}")
    public List<Ingredient> getIngredientsByCardId(@PathVariable(value = "id")Integer id){
        return ingredientService.getIngredientsByCardId(id);
    }

    //pobiera listę wszystkich składników z tabeli
    @GetMapping("/ingredient/all")
    public List<Ingredient> getAll(){
        return ingredientRepository.findAll();
    }

    //sumuje wszytskie skladniki na podstawie id użytkownika
    @GetMapping("/ingredient/user/{id}")
    public List<Ingredient> sumIngredientsByUserId(@PathVariable(value = "id")Long id){
        return ingredientService.sumAllIngredientsByUserId(id);
    }
}

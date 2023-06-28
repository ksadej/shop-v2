package com.example.shopv2.controller;

import com.example.shopv2.model.Basket;
import com.example.shopv2.model.Recipes;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BasketController {

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    // zapisuje w tabeli Ingredient składniki na podstawie id przepisu
    @PostMapping(path = "/api/basket/v1/all/{id}")
    public void saveIngredientAndRecipesByRecipesId(@PathVariable(value = "id")Integer id){
        basketService.saveRecipesAndIngredientsByRecipesId(id);

    }

    //pobiera wszystkie składniki recepty na podstawie id użytkownika
    @GetMapping(path = "/api/basket/user/{id}")
    public List<Basket> getAllIngredientsByUserId(@PathVariable(value = "id")Long id){
        return basketService.getCardByUserId(id);
    }

    //zapisuje składniki oraz wartości odzywcze na podstawie id przepisu
    @PostMapping(path = "/api/basket/all/{id}")
    public void saveIngredientAndNutritionByRecipesId(@PathVariable(value = "id")Integer id){
        basketService.saveAllByRecipesId(id);
    }

    //zapisuje wartości odzywcze na podstawie id przepisu
    @PostMapping(path = "/api/basket/nutrition/{id}")
    public void saveNutritionByRecipesId(@PathVariable(value = "id")Integer id){
        basketService.saveNutritionByIngredientId(id);
    }

}

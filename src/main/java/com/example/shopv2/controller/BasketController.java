package com.example.shopv2.controller;

import com.example.shopv2.model.Basket;
import com.example.shopv2.model.Nutrition;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BasketController {

    private final BasketService basketService;
    private final NutritionService nutritionService;

    @Autowired
    public BasketController(BasketService basketService, NutritionService nutritionService) {
        this.basketService = basketService;
        this.nutritionService = nutritionService;
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

    @GetMapping(path = "/api/basket/sum")
    public List<Nutrition> sumNutritionByBasketIdXX(){
        return basketService.summingNutritionByBasket();
    }
}

package com.example.shopv2.controller;

import com.example.shopv2.service.dto.BasketDTO;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.model.Nutrition;
import com.example.shopv2.pojo.RecipesPojo;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping(path = "/api/basket/user")
    public List<BasketDTO> getAllIngredientsByUserId(){
        return basketService.getBasketByUser();
    }

    //zapisuje składniki oraz wartości odzywcze na podstawie id przepisu
    @PostMapping(path = "/api/basket/all/{id}")
    public void saveIngredientAndNutritionByRecipesId(@PathVariable(value = "id")Integer id){
        basketService.saveAllByRecipesId(id);
    }

    //zapisuje wartości odzywcze na podstawie id składniku
    @PostMapping(path = "/api/basket/nutrition/{id}")
    public void saveNutritionByIngredient(@PathVariable(value = "id")Integer id){
        basketService.saveNutritionByIngredientId(id);
    }

    @GetMapping(path = "/api/basket/recipes")
    public List<RecipesPojo> recipesListByUserId(){
        return basketService.getListOfRecipesByUser();
    }

    @GetMapping(path = "/api/basket/sumNutrition")
    public List<Nutrition> sumNutritionByBasketId(){
        return basketService.summingNutritionByBasket();
    }

    @GetMapping(path = "/api/basket/sumIngredient")
    public List<Ingredient> sumIngredientByBasketId(){
        return basketService.sumIngredientByBasketId();
    }

    @GetMapping(path = "/api/basket/sum/price")
    public Double sumPriceByBasketId(){
        return basketService.sumPrice();
    }

    @GetMapping(path = "/api/basket/filter")
    public List<BasketDTO> filterMealsBetweenDate(@RequestParam String fromDate, @RequestParam String toDate){
        return basketService.filterBasketBetweenDate(fromDate, toDate);
    }

    @GetMapping(path = "/api/basket/filter/date")
    public List<BasketDTO> filterMealsDate(@RequestParam Map<String, String> filter){
        return basketService.getAllFilteredBasket(filter);
    }

    @DeleteMapping(path = "/api/basket")
    public void deleteBasket(){
        basketService.deleteBasketByUser();
    }
}

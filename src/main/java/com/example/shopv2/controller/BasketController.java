package com.example.shopv2.controller;

import com.example.shopv2.model.Basket;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BasketController {

    private final BasketService basketService;
    private final BasketRepository basketRepository;

    @Autowired
    public BasketController(BasketService basketService, BasketRepository basketRepository) {
        this.basketService = basketService;
        this.basketRepository = basketRepository;
    }

    // zapisuje w tabeli Ingredient składniki na podstawie id przepisu
    @PostMapping(path = "/api/basket/all/{id}")
    public void saveIngredientByRecipesId(@PathVariable(value = "id")Integer id){
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
        basketService.saveIngredientsAndNutritionByRecipesId(id);
    }

    //zapisuje wartości odzywcze na podstawie id przepisu
    @PostMapping(path = "/api/basket/nutrition/{id}")
    public void saveNutritionByRecipesId(@PathVariable(value = "id")Integer id){
        basketService.saveNutritionByIngredientId(id);
    }


    //testy
//    @GetMapping(path = "/api/basket/nutrition/t/{id}")
//    public ArrayList<Ingredient> getAllIngredientsByCardId(@PathVariable(value = "id")Long id){
//        return basketService.get(id);
//    }

//    @GetMapping(path = "/api/basket/nutrition/t2/{id}")
//    public Card getAllCards(@PathVariable(value = "id")Long id){
//        return basetRepository.findByIdUser(id);
//    }
}

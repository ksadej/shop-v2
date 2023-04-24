package com.example.shopv2.controller;

import com.example.shopv2.model.Ingredient;
import com.example.shopv2.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // zapisuje w tabeli Ingredient składnikie na podstawie id przepisu
    @GetMapping(path = "/api/card/{id}")
    public void saveIngredientByRecipesId(@PathVariable(value = "id")Integer id){
        cardService.saveRecipesIngredientsByRecipesId(id);
    }

    @GetMapping(path = "/api/card/user/{id}")
    public List<Ingredient> getAllIngredientsByUserId(@PathVariable(value = "id")Long id){
        return cardService.getCardByUserId(id);
    }


    ///test
    @GetMapping(path = "/api/card/test/{id}")
    public void saveIngredientByRecipesIdTEST(@PathVariable(value = "id")Integer id){
        cardService.saveRecipesIngredientsByRecipesIdTEST(id);
    }

    @GetMapping(path = "/api/card/test2/{id}")
    public void saveIngredientByRecipesIdTEST2(@PathVariable(value = "id")Integer id){
        cardService.saveNutritionByIngredientIdTEST(id);
    }


}

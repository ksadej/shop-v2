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

    @GetMapping(path = "/api/card/{id}")
    public void saveIngredientByRecipesId(@PathVariable(value = "id")Integer id){
        cardService.saveRecipesIngredientsByRecipesId(id);
    }

    @GetMapping(path = "/api/card/user/{id}")
    public List<Ingredient> getAllIngredientsByUserId(@PathVariable(value = "id")Long id){
        return cardService.getCardByUserId(id);
    }

}

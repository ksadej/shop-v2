package com.example.shopv2.controller;

import com.example.shopv2.model.Card;
import com.example.shopv2.repository.CardRepository;
import com.example.shopv2.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CardController {

    private final CardService cardService;
    private final CardRepository cardRepository;

    @Autowired
    public CardController(CardService cardService, CardRepository cardRepository) {
        this.cardService = cardService;
        this.cardRepository = cardRepository;
    }

    // zapisuje w tabeli Ingredient składniki na podstawie id przepisu
    @PostMapping(path = "/api/card/ingredient")
    public void saveIngredientByRecipesId(@PathVariable(value = "id")Integer id){
        cardService.saveRecipesIngredientsByRecipesId(id);
    }

    //pobiera wszystkie składniki recepty na podstawie id użytkownika
    @GetMapping(path = "/api/card/user/{id}")
    public List<Card> getAllIngredientsByUserId(@PathVariable(value = "id")Long id){
        return cardService.getCardByUserId(id);
    }

    //zapisuje składniki oraz wartości odzywcze na podstawie id przepisu
    @PostMapping(path = "/api/card/all/{id}")
    public void saveIngredientAndNutritionByRecipesId(@PathVariable(value = "id")Integer id){
        cardService.saveIngredientsAndNutritionByRecipesId(id);
    }

    //zapisuje wartości odzywcze na podstawie id przepisu
    @PostMapping(path = "/api/card/nutrition/{id}")
    public void saveNutritionByRecipesId(@PathVariable(value = "id")Integer id){
        cardService.saveNutritionByIngredientId(id);
    }


    //testy
//    @GetMapping(path = "/api/card/nutrition/t/{id}")
//    public ArrayList<Ingredient> getAllIngredientsByCardId(@PathVariable(value = "id")Long id){
//        return cardService.get(id);
//    }

//    @GetMapping(path = "/api/card/nutrition/t2/{id}")
//    public Card getAllCards(@PathVariable(value = "id")Long id){
//        return cardRepository.findByIdUser(id);
//    }
}

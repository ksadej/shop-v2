package com.example.shopv2.service;

import com.example.shopv2.model.Basket;
import com.example.shopv2.model.Card;
import com.example.shopv2.repository.CardRepository;
import com.example.shopv2.service.dto.RecipesIngredientResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;

    private final RecipesService recipesService;

    public CardService(CardRepository cardRepository, RecipesService recipesService) {
        this.cardRepository = cardRepository;
        this.recipesService = recipesService;
    }

    public void saveRecipesIngredientsByRecipesIdInCard(Integer id){

        List<RecipesIngredientResponse> recipesIngredientResponses = recipesService.getIngredientByRecipesId(id);
        System.out.println("dane jakie dostałem z  przepisów: "+ recipesIngredientResponses);

        Card card = new Card();

        List<String> names = recipesIngredientResponses.stream().map(c -> c.getName()).collect(Collectors.toList());
        System.out.println("Lista nazw produktów: "+names);

        for(int i=0; i< names.size(); i++){
            Card cc = Card
                    .builder()
                    .idIngredient(recipesIngredientResponses.stream().map(c -> c.getId()).collect(Collectors.toList()).get(i))
                    .aisle(recipesIngredientResponses.stream().map(c -> c.getAisle()).collect(Collectors.toList()).get(i))
                    .amount(recipesIngredientResponses.stream().map(c -> c.getAmount()).collect(Collectors.toList()).get(i))
                    .name(recipesIngredientResponses.stream().map(c -> c.getName()).collect(Collectors.toList()).get(i))
                    .consistency(recipesIngredientResponses.stream().map(c -> c.getConsistency()).collect(Collectors.toList()).get(i))
                    .image(recipesIngredientResponses.stream().map(c -> c.getImage()).collect(Collectors.toList()).get(i))
                    .unit(recipesIngredientResponses.stream().map(c -> c.getUnit()).collect(Collectors.toList()).get(i))
                    .original(recipesIngredientResponses.stream().map(c -> c.getOriginal()).collect(Collectors.toList()).get(i))
                    .idRecipes(id)
                    .build();
            cardRepository.save(cc);
        }
    }

    public List<Card> getCardByUserId(Long id){
        return cardRepository.findByIdUser(id);
    }


}

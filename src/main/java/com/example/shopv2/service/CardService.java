package com.example.shopv2.service;

import com.example.shopv2.model.Card;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.repository.CardRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.service.dto.RecipesIngredientResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardRepository cardRepository;

    private final RecipesService recipesService;

    private final IngredientRepository ingredientRepository;

    public CardService(CardRepository cardRepository, RecipesService recipesService, IngredientRepository ingredientRepository) {
        this.cardRepository = cardRepository;
        this.recipesService = recipesService;
        this.ingredientRepository = ingredientRepository;
    }

//    public void saveRecipesIngredientsByRecipesIdInCard(Integer id){
//
//        List<RecipesIngredientResponse> recipesIngredientResponses = recipesService.getIngredientByRecipesId(id);
//        System.out.println("dane jakie dostałem z  przepisów: "+ recipesIngredientResponses);
//
//        Card card = new Card();
//
//        List<String> names = recipesIngredientResponses.stream().map(c -> c.getName()).collect(Collectors.toList());
//        System.out.println("Lista nazw produktów: "+names);
//
//        for(int i=0; i< names.size(); i++){
//            Card cc = Card
//                    .builder()
////                    .idIngredient(recipesIngredientResponses.stream().map(c -> c.getId()).collect(Collectors.toList()).get(i))
//                    .aisle(recipesIngredientResponses.stream().map(c -> c.getAisle()).collect(Collectors.toList()).get(i))
//                    .amount(recipesIngredientResponses.stream().map(c -> c.getAmount()).collect(Collectors.toList()).get(i))
//                    .name(recipesIngredientResponses.stream().map(c -> c.getName()).collect(Collectors.toList()).get(i))
//                    .consistency(recipesIngredientResponses.stream().map(c -> c.getConsistency()).collect(Collectors.toList()).get(i))
//                    .image(recipesIngredientResponses.stream().map(c -> c.getImage()).collect(Collectors.toList()).get(i))
//                    .unit(recipesIngredientResponses.stream().map(c -> c.getUnit()).collect(Collectors.toList()).get(i))
//                    .original(recipesIngredientResponses.stream().map(c -> c.getOriginal()).collect(Collectors.toList()).get(i))
//                    .idRecipes(id)
//                    .build();
//            cardRepository.save(cc);
//        }
//    }


    public void saveRecipesIngredientsByRecipesId(Integer id){

        List<RecipesIngredientResponse> recipesIngredientResponses = recipesService.getIngredientByRecipesId(id);
        System.out.println("dane jakie dostałem z  przepisów: "+ recipesIngredientResponses);

        List<String> names = recipesIngredientResponses.stream().map(c -> c.getName()).collect(Collectors.toList());
        System.out.println("Lista nazw produktów: "+names);

        Card card = new Card();
        for(int i=0; i< names.size(); i++){
            Ingredient cc = Ingredient
                    .builder()
                    .aisle(recipesIngredientResponses.stream().map(c -> c.getAisle()).collect(Collectors.toList()).get(i))
                    .amount(recipesIngredientResponses.stream().map(c -> c.getAmount()).collect(Collectors.toList()).get(i))
                    .name(recipesIngredientResponses.stream().map(c -> c.getName()).collect(Collectors.toList()).get(i))
                    .consistency(recipesIngredientResponses.stream().map(c -> c.getConsistency()).collect(Collectors.toList()).get(i))
                    .image(recipesIngredientResponses.stream().map(c -> c.getImage()).collect(Collectors.toList()).get(i))
                    .unit(recipesIngredientResponses.stream().map(c -> c.getUnit()).collect(Collectors.toList()).get(i))
                    .original(recipesIngredientResponses.stream().map(c -> c.getOriginal()).collect(Collectors.toList()).get(i))
                    .card(card)
                    .build();

            card.getIngredients().add(cc);

            cardRepository.save(card);
        }
    }

    //TEST
//    public List<CardTwo> getCardByUserIdTEST(Long id){
//        List<CardTwo>  cardTwoList =  cardTwoRepository.findByIdUser(id);
//        return cardTwoList;
//    }

    public List<Ingredient> getCardByUserId(Long id){
        Card cardList =  cardRepository.findByIdUser(id);
        System.out.println("Id Usera przy pobieraniu karty produktów: "+cardList.getId());

        return ingredientRepository.findByCardId(cardList.getId());
    }

}

package com.example.shopv2.service;


import com.example.shopv2.model.Card;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.repository.CardRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientService.class.getName());

    private final CardRepository cardRepository;
    private final IngredientRepository ingredientRepository;
    private final RestTemplate restTemplate;

    private final HttpHeaders httpHeaders;

    @Autowired
    public IngredientService(CardRepository cardRepository,
                             IngredientRepository ingredientRepository,
                             RestTemplate restTemplate,
                             @Qualifier("recipesHeaders") HttpHeaders httpHeaders) {
        this.cardRepository = cardRepository;
        this.ingredientRepository = ingredientRepository;
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }


    //pobiera składniki z przepisu na podstawie id przepisu
    public List<RecipesIngredientPojo> getIngredientByRecipesId(Integer id){
        LOGGER.info("Getting ingredients by recipes id");
        LOGGER.debug("Recipes id: "+id);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<RecipesIngredientPojo> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/"+id+"/information?includeNutrition=false",
                        HttpMethod.GET,
                        httpEntity,
                        RecipesIngredientPojo.class);

        LOGGER.debug("List of ingredients"+ entity.getBody().getExtendedIngredients());
        ArrayList<RecipesIngredientPojo> recipesIngredientResponses = entity.getBody().getExtendedIngredients();

        return recipesIngredientResponses;
    }

    //pobiera listę wszystkich składkików
    public List<Ingredient> getIngredientsByCardId(Integer id){
        return ingredientRepository.findAllByCardId(Long.valueOf(id));
    }

    //funkcja do podsumowania ilości produktów które mamy zakupić
    public List<Ingredient> sumAllIngredientsByUserId(Long id){

        // lista card id
        LOGGER.info("Sum ingredients by USER id");
        LOGGER.debug("USER id: "+id);
        List<Long> listOfCardIds = cardRepository.findByIdUser(id)
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());

        //lista składników pobranych na podstawie card id
        List<Ingredient> ingredients = ingredientRepository.findAllByCardIdIn((ArrayList<Long>) listOfCardIds);

        //lista nazw produktów z usuniętymi duplikatami
        List<String> ingredientNames = ingredients.stream()
                .map(x ->x.getName())
                .distinct()
                .collect(Collectors.toList());

        //lista do której zapisuję nowe zbudowane obiekty
        List<Ingredient> newIngredients = new ArrayList<>();

        for(String st : ingredientNames){
            Ingredient ingredient = Ingredient
                    .builder()
                    .name(st)
                    .amount(ingredients.stream()
                            .filter(name -> name.getName().equals(st))
                            .mapToDouble(amout -> amout.getAmount())
                            .sum())
                    .build();
            newIngredients.add(ingredient);
        }

        return newIngredients;
    }
}

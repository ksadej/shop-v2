package com.example.shopv2.service;

import com.example.shopv2.model.Ingredient;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService{

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientService.class.getName());

    private final BasketRepository basketRepository;
    private final IngredientRepository ingredientRepository;


    @Autowired
    public IngredientService(BasketRepository basketRepository,
                             IngredientRepository ingredientRepository) {
        this.basketRepository = basketRepository;
        this.ingredientRepository = ingredientRepository;
    }

    //pobiera składniki z przepisu na podstawie id przepisu
    public List<RecipesIngredientPojo> getIngredientByRecipesId(Integer id){
        LOGGER.info("Getting ingredients by recipes id");
        LOGGER.debug("Recipes id: "+id);
        ArrayList<RecipesIngredientPojo> extendedIngredients =
                Connection.externalApiConnectionGET(
                        "https://api.spoonacular.com/recipes/"+id+"/information?includeNutrition=false", RecipesIngredientPojo.class).getExtendedIngredients();
        LOGGER.debug("List of ingredients"+ extendedIngredients);

        return extendedIngredients;
    }

    //pobiera listę wszystkich składkików
    public List<Ingredient> getIngredientsByCardId(Integer id){
        return ingredientRepository.findAllByBasketId(Long.valueOf(id));
    }

    //funkcja do podsumowania ilości produktów które mamy zakupić
    public List<Ingredient> sumAllIngredientsByUserId(Long id){

        // lista card id
        LOGGER.info("Sum ingredients by USER id");
        LOGGER.debug("USER id: "+id);
        List<Long> listOfCardIds = basketRepository.findByIdUser(id)
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());

        //lista składników pobranych na podstawie card id
        List<Ingredient> ingredients = ingredientRepository.findAllByBasketIdIn((ArrayList<Long>) listOfCardIds);

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



    //filtry: po cenie produktu od najmniejszego do największego
    //filtry: po kategorii: owoce, warzywa itd.
}

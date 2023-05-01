package com.example.shopv2.service;


import com.example.shopv2.model.Ingredient;
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
import java.util.List;

@Service
public class IngredientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientService.class.getName());

    private  final IngredientRepository ingredientRepository;
    private final RestTemplate restTemplate;

    private final HttpHeaders httpHeaders;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, RestTemplate restTemplate, @Qualifier("recipesHeaders") HttpHeaders httpHeaders) {
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

    public List<Ingredient> getAllIngredients(){
        return ingredientRepository.findAll();
    }
}

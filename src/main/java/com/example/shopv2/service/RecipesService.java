package com.example.shopv2.service;

import com.example.shopv2.pojo.RecipesPojo;
import com.example.shopv2.pojo.ResultPojo;
import com.example.shopv2.validator.RecipesValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RecipesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipesService.class.getName());

    private final RestTemplate restTemplate;
    private final RecipesValidator recipesValidator;
    private final HttpHeaders httpHeaders;


    @Autowired
    public RecipesService(RestTemplate restTemplate,
                          @Qualifier("recipesHeaders") HttpHeaders httpHeaders,
                          RecipesValidator recipesValidator) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.recipesValidator = recipesValidator;
    }

    //getting list of product by User Id Basket and finding on this list of product proposed recipes
//    public List<RecipesPojo> getRecipesByIngredients(Long id){
//        LOGGER.info("Getting recipes by ingredients id");
//        LOGGER.debug("Ingredient id: "+id);
//        List<String> products = basketService.getListOfProductNames(id);
//        String ingredients = products.toString();
//
//        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
//
//        ResponseEntity<RecipesPojo[]> entity = restTemplate
//                .exchange("https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredients,
//                        HttpMethod.GET,
//                        httpEntity,
//                        RecipesPojo[].class);
//
//        List<RecipesPojo> recipesPojo = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
//
//        return recipesPojo;
//    }

    public List<RecipesPojo> getRecipesByType(String type){
        recipesValidator.getRecipesByTypeValidate(type);

        LOGGER.info("Getting recipes by typ");
        LOGGER.debug("Type of recipes: "+type);


        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<ResultPojo> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/complexSearch?type="+type ,
                        HttpMethod.GET,
                        httpEntity,
                        ResultPojo.class);

        List<RecipesPojo> recipesResponses = entity.getBody().getResults();
        LOGGER.debug("List of recipes by type "+ entity.getBody().getResults());
        return recipesResponses;

    }

    public RecipesPojo getRecipesById(Integer id){
        LOGGER.info("Getting recipes by id");
        LOGGER.debug("ID of recipes: "+id);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        System.out.println("Identyfikator: "+id);
        ResponseEntity<RecipesPojo> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/"+id+"/information?includeNutrition=false",
                        HttpMethod.GET,
                        httpEntity,
                        RecipesPojo.class);
        System.out.println(entity.getBody());
        RecipesPojo rootResponses = entity.getBody();
        LOGGER.debug("Recipes by id: "+ entity.getBody());

        return rootResponses;
    }
}

package com.example.shopv2.service;

import com.example.shopv2.pojo.RecipesPojo;
import com.example.shopv2.pojo.ResultPojo;
import com.example.shopv2.wastes.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class RecipesService {

    private final RestTemplate restTemplate;

    private final HttpHeaders httpHeaders;

    private final BasketService basketService;
    @Autowired
    public RecipesService(RestTemplate restTemplate, @Qualifier("recipesHeaders") HttpHeaders httpHeaders, BasketService basketService) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.basketService = basketService;
    }

    //getting list of product by User Id Basket and finding on this list of product proposed recipes

    public List<RecipesPojo> getRecipesByIngredients(Long id){
        List<String> products = basketService.getListOfProductNames(id);
        String ingredients = products.toString();

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<RecipesPojo[]> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredients,
                        HttpMethod.GET,
                        httpEntity,
                        RecipesPojo[].class);

        List<RecipesPojo> recipesPojo = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();

        return recipesPojo;
    }

    public List<RecipesPojo> getRecipesByType(String type){
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<ResultPojo> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/complexSearch?type="+type ,
                        HttpMethod.GET,
                        httpEntity,
                        ResultPojo.class);

        List<RecipesPojo> recipesResponses = entity.getBody().getResults();

        return recipesResponses;
    }

    public RecipesPojo getRecipesById(Integer id){
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        System.out.println("Identyfikator: "+id);
        ResponseEntity<RecipesPojo> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/"+id+"/information?includeNutrition=false",
                        HttpMethod.GET,
                        httpEntity,
                        RecipesPojo.class);
        System.out.println(entity.getBody());
        RecipesPojo rootResponses = entity.getBody();

        return rootResponses;
    }
}

package com.example.shopv2.service;

import com.example.shopv2.service.dto.RecipesIngredientResponse;
import com.example.shopv2.service.dto.RecipesResponse;
import com.example.shopv2.service.dto.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
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

    public List<RecipesResponse> getRecipesByIngredients(Long id){
        List<String> products = basketService.getListOfProductNames(id);
        String ingredients = products.toString();

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<RecipesResponse[]> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredients,
                        HttpMethod.GET,
                        httpEntity,
                        RecipesResponse[].class);

        List<RecipesResponse> recipesResponses = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();

        return recipesResponses;
    }

    public List<RecipesResponse> getRecipesByType(String type){
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<ResultResponse> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/complexSearch?type="+type ,
                        HttpMethod.GET,
                        httpEntity,
                        ResultResponse.class);

        List<RecipesResponse> recipesResponses = entity.getBody().getResults();

        return recipesResponses;
    }


    public List<RecipesIngredientResponse> getIngredientByRecipesId(Integer id){
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        System.out.println("Identyfikator: "+id);
        ResponseEntity<RecipesIngredientResponse> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/"+id+"/information?includeNutrition=false",
                        HttpMethod.GET,
                        httpEntity,
                        RecipesIngredientResponse.class);
        System.out.println(entity.getBody().getExtendedIngredients());
        ArrayList<RecipesIngredientResponse> recipesIngredientResponses = entity.getBody().getExtendedIngredients();

        return recipesIngredientResponses;
    }


    public RecipesResponse getRecipesById(Integer id){
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        System.out.println("Identyfikator: "+id);
        ResponseEntity<RecipesResponse> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/"+id+"/information?includeNutrition=false",
                        HttpMethod.GET,
                        httpEntity,
                        RecipesResponse.class);
        System.out.println(entity.getBody());
        RecipesResponse rootResponses = entity.getBody();

        return rootResponses;
    }
}

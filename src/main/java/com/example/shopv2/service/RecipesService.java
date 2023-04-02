package com.example.shopv2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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


    public String getRecipesByIngredients(String ingredients){

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredients,
                        HttpMethod.GET,
                        httpEntity,
                        String.class);

        System.out.println(entity.getBody());
        return entity.getBody();
    }

    //test
    public String getRecipesByIngredients1(Long id){

        List<String> products = basketService.getListOfProductNames(id);

        String ingredients = products.toString();

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/findByIngredients?ingredients=" + ingredients,
                        HttpMethod.GET,
                        httpEntity,
                        String.class);

        System.out.println(entity.getBody());
        return entity.getBody();
    }
}

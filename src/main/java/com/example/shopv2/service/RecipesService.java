package com.example.shopv2.service;

import com.example.shopv2.service.dto.RecipesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
}

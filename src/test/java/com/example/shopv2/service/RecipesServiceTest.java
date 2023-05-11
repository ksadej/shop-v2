package com.example.shopv2.service;

import com.example.shopv2.validator.RecipesValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

class RecipesServiceTest {

    private RecipesService recipesService;
    private RecipesValidator recipesValidator;
    private RestTemplate restTemplate;

    private HttpHeaders httpHeaders;
    public void setup(){
        recipesService = Mockito.mock(RecipesService.class);
//    recipesService = new RecipesService(basketRepository, restTemplate, httpHeaders, recipesValidator);
    }


    @Test
    public void getRecipesByType_checkIfTypeNoExist_throwException(){
        //given
        final String actualType = "x";
        //when
       // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> recipesService.getRecipesByType(actualType));

    }
}
package com.example.shopv2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RecipesServiceTest {

    private RecipesService recipesService;
    public void setup(){
    recipesService = Mockito.mock(RecipesService.class);
    }

    @Test
    public void getRecipesByType_checkIfTypeNoExist_throwException(){
        //given
        final String type = null;

        //when//then
        Assertions.assertThrows(NullPointerException.class, () -> recipesService.getRecipesByType(type));
    }
}
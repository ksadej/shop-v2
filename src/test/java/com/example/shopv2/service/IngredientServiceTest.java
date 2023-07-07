package com.example.shopv2.service;

import com.example.shopv2.mapper.IngredientMapper;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

class IngredientServiceTest {

    @InjectMocks
    private IngredientService ingredientService;
    @Mock
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;

    @BeforeEach
    public void setup(){

    }

    @Test
    void getIngredientByRecipesId_getData_dataGetted() throws URISyntaxException {


    }
}
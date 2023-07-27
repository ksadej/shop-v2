package com.example.shopv2.service;

import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.net.URISyntaxException;
import static org.mockito.Mockito.*;

@SpringBootTest
class IngredientServiceTestIntegration {

    private WireMockServer wireMockServer;
    private IngredientService ingredientService;
    BasketRepository basketRepository;
    IngredientRepository ingredientRepository;
    final RestTemplate restTemplate = new RestTemplate();
    final HttpHeaders httpHeaders = new HttpHeaders();

    @BeforeEach
    public void setup(){

    }

    @Test
    void getIngredientByRecipesId_getData_dataGetted() throws URISyntaxException {


    }
}
package com.example.shopv2.service;

import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
@Scope(value = "singleton")
public class ConnectionImpl{

    private static RestTemplate restTemplate = null;
    private static HttpHeaders httpHeaders = null;


    protected ConnectionImpl(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    public static <T> T getIngredientByRecipesId4(String URL, Class<T> clazz){

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        T responseEntity = restTemplate.exchange(
                        URL,
                        HttpMethod.GET,
                        entity,
                        clazz).getBody();

        return responseEntity;
    }




}

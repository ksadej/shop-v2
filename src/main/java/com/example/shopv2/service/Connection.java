package com.example.shopv2.service;

import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Scope(value = "singleton")
public class Connection {

    private static RestTemplate restTemplate = null;
    private static HttpHeaders httpHeaders = null;


    private Connection(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        Connection.restTemplate = restTemplate;
        Connection.httpHeaders = httpHeaders;
    }

    protected static <T> T externalApiConnectionGET(String URL, Class<T> clazz){

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        T responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, clazz).getBody();

        return responseEntity;
    }
}

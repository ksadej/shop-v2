package com.example.shopv2.service;

import com.example.shopv2.model.Product;
import com.example.shopv2.repository.ProductRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ProductRepository productRepository;

    public ProductService(RestTemplate restTemplate, HttpHeaders httpHeaders, ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.productRepository = productRepository;
    }

    public String getProductByName(String name){

        if(name.isEmpty()){
            throw new IllegalArgumentException("Name is empty");
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> entity = restTemplate
                .exchange("https://nutrition-by-api-ninjas.p.rapidapi.com/v1/nutrition?query=" + name,
                        HttpMethod.GET,
                        httpEntity,
                        String.class);

        if(entity.getBody().equals("[]")){
            throw new IllegalArgumentException("Product with this name does not exist");
        }

        return entity.getBody();
    }



}

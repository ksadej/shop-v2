package com.example.shopv2.wastes;

import com.example.shopv2.controller.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(RestTemplate restTemplate, @Qualifier("productHeaders") HttpHeaders httpHeaders, ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.productRepository = productRepository;
    }

    public String getProductByName(String name){

        if(name.isEmpty()){
            throw new IllegalArgumentException("Name is empty!");
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


    //testy
    public List<ProductResponse> getProductByName1(String name){

        if(name.isEmpty()){
            throw new IllegalArgumentException("Name is empty!");
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<ProductResponse[]> entity = restTemplate
                .exchange("https://nutrition-by-api-ninjas.p.rapidapi.com/v1/nutrition?query=" + name,
                        HttpMethod.GET,
                        httpEntity,
                        ProductResponse[].class);

        if(entity.getBody().equals("[]")){
            throw new IllegalArgumentException("Product with this name does not exist");
        }

        List<ProductResponse> productResponseList = Arrays.stream(entity.getBody()).toList();
        return productResponseList;
    }

}
package com.example.shopv2.service;

import com.example.shopv2.controller.dto.BasketResponse;
import com.example.shopv2.model.Basket;
import com.example.shopv2.repository.BasketRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

@Service
public class BasketService {

    final private BasketRepository basketRepository;
    final private ProductService productService;

    @Autowired
    public BasketService(BasketRepository basketRepository, ProductService productService) {
        this.basketRepository = basketRepository;
        this.productService = productService;
    }

    public void saveProduct(Basket basket){
        basketRepository.save(basket);
    }

    public void saveProductByName(String name){
        String jsonString = productService.getProductByName(name);
        System.out.println(jsonString);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Basket> basketList = List.of(objectMapper.readValue(jsonString, Basket[].class));

            basketRepository.saveAll(basketList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //List of sum all nutrition product of user id
    public BasketResponse sumAllNutrition(Long id){
        List<Basket> basket = basketRepository.findByUserId(id);

        BasketResponse build = BasketResponse
                .builder()
                .calories(basket.stream().mapToDouble(f -> f.getCalories()).sum())
                .serving_size_g(basket.stream().mapToDouble(f -> f.serving_size_g).sum())
                .fat_total_g(basket.stream().mapToDouble(f -> f.fat_total_g).sum())
                .fat_saturated_g(basket.stream().mapToDouble(f -> f.fat_saturated_g).sum())
                .protein_g(basket.stream().mapToDouble(f -> f.protein_g).sum())
                .sodium_mg(basket.stream().mapToInt(f -> f.sodium_mg).sum())
                .potassium_mg(basket.stream().mapToInt(f -> f.potassium_mg).sum())
                .cholesterol_mg(basket.stream().mapToInt(f -> f.cholesterol_mg).sum())
                .carbohydrates_total_g(basket.stream().mapToDouble(f -> f.carbohydrates_total_g).sum())
                .fiber_g(basket.stream().mapToDouble(f -> f.fiber_g).sum())
                .sugar_g(basket.stream().mapToDouble(f -> f.sugar_g).sum())
                .build();

        return build;
    }

    //list of products in basket by user id
    public List<Basket> getBasketByUserId(Long id){
        return basketRepository.findByUserId(id);
    }

    public void deleteProductInBasket(Long id){
        basketRepository.deleteById(id);
    }
}

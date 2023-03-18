package com.example.shopv2.controller;

import com.example.shopv2.controller.dto.BasketResponse;
import com.example.shopv2.model.Basket;
import com.example.shopv2.service.BasketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BasketController {

    final private BasketService basketService;


    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping(path = "/api/basket")
    public void saveProductInBasketByName(@RequestParam String name){
        basketService.saveProductByName(name);
    }

    @GetMapping(path = "/api/basket/nutrition")
    public BasketResponse sumAllNutrition(){
        return basketService.sumAllNutrition();
    }

}

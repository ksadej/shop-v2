package com.example.shopv2.controller;
import com.example.shopv2.controller.dto.BasketResponse;
import com.example.shopv2.service.BasketService;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping(path = "/api/v1/basket")
    public BasketResponse sumAllNutrition(@RequestParam Long id){
        return basketService.sumAllNutrition(id);
    }



}

package com.example.shopv2.controller;
import com.example.shopv2.controller.dto.BasketResponse;
import com.example.shopv2.model.Basket;
import com.example.shopv2.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BasketController {

    final private BasketService basketService;


    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping(path = "/api/basket")
    public ResponseEntity<?> saveProductInBasketByName(@RequestParam String name){
        basketService.saveProductByName(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/api/v1/basket")
    public BasketResponse sumAllNutrition(@RequestParam Long id){
        return basketService.sumAllNutrition(id);
    }

    @DeleteMapping(path = "/api/basket")
    public void removeProductFromBasket(@RequestParam Long id){
        basketService.deleteProductInBasket(id);
    }

    @GetMapping(path = "/api/v1/test/basket")
    public List<String> listOfProductNames(@RequestParam Long id){
        return basketService.getListOfProductNames(id);
    }

    @GetMapping(path = "/api/v1/basket/user")
    public List<Basket> getBasketByUserId(@RequestParam Long id){
        return basketService.getBasketByUserId(id);
    }
}

package com.example.shopv2.wastes;
import com.example.shopv2.controller.dto.BasketResponse;
import com.example.shopv2.wastes.Basket;
import com.example.shopv2.wastes.BasketService;
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

    //zapisuje produkt po nazwie w bazie danych
    @GetMapping(path = "/api/basket")
    public ResponseEntity<?> saveProductInBasketByName(@RequestParam String name){
        basketService.saveProductByName(name);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    //sumuje wszystkie wartości w produktach na podstawie id użytkownika
    @GetMapping(path = "/api/basket/v1")
    public BasketResponse sumAllNutrition(@RequestParam Long id){
        return basketService.sumAllNutrition(id);
    }

    @DeleteMapping(path = "/api/basket")
    public void removeProductFromBasket(@RequestParam Long id){
        basketService.deleteProductInBasket(id);
    }

    //lista nazw produktów w koszyku na podstawie numeru id użytkownika
    @GetMapping(path = "/api/basket/v1/list")
    public List<String> listOfNameProductsInBasket(@RequestParam Long id){
        return basketService.getListOfProductNames(id);
    }

    @GetMapping(path = "/api/basket/v1/user")
    public List<Basket> getBasketByUserId(@RequestParam Long id){
        return basketService.getBasketByUserId(id);
    }
}

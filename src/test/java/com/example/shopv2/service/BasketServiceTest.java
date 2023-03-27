package com.example.shopv2.service;

import com.example.shopv2.model.Basket;
import com.example.shopv2.repository.BasketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketServiceTest {

    private BasketRepository basketRepository;
    private BasketService basketService;
    private ProductService productService;

    @BeforeEach
    public void setup(){
        basketRepository = Mockito.mock(BasketRepository.class);
        productService = Mockito.mock(ProductService.class);
        basketService = new BasketService(basketRepository, productService);
    }

    @Test
    public void getBasketByUserId_returnBasketByUserId(){

        //given
        final Basket expectedBasket = new Basket(1l, 22l, "nutella", 2151.2, 400.0, 121.2, 114.0, 21.6, 164, 604, 0, 252.0, 22.0, 216.4);
        final Long userId = 22l;
        Mockito.when(basketRepository.findByUserId(userId)).thenReturn(List.of(expectedBasket));

        //when
        basketRepository.findByUserId(userId);

        //then
        Mockito.verify(basketRepository).findByUserId(userId);
    }
}
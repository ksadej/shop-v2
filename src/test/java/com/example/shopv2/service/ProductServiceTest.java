package com.example.shopv2.service;

import com.example.shopv2.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private ProductRepository productRepository;
    private ProductService productService;


    @BeforeEach
    public void setup(){
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(restTemplate, httpHeaders, productRepository);
    }

    @Test
    public void getProductByName_checkIfNameFieldInIncorrect_throwIllegalArgument(){
        //given
        final String name = "";

        //when//then
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.getProductByName(name));
    }

    @Test
    public void getProductByName_checkIfReturnedBodyIsIncorrect_throwIllegalArgument(){
        //given
        final String name = "Nutella";
        Mockito.when(productService.getProductByName(name)).thenReturn("[]");

        //when
        productService.getProductByName(name);

        // then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(productService).getProductByName(argumentCaptor.capture());

    }

}
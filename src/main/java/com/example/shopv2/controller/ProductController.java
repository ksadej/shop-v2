package com.example.shopv2.controller;

import com.example.shopv2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {

    final private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/product")
    public ResponseEntity<String> getProductByName(@RequestParam String name){
        String productName = productService.getProductByName(name);
        return new ResponseEntity<>(productName, HttpStatus.OK);
    }


    //testy BasketService
    //uwtorzyc branch dla security
    //wykonac security aplikacji outh2
    //dodać do funkcji ResponseEntity
}

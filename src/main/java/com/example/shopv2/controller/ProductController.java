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
        String productResponse = productService.getProductByName(name);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    //funkcja do usuwania produktu z koszyka
}

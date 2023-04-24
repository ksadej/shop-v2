package com.example.shopv2.wastes;

import com.example.shopv2.controller.dto.ProductResponse;
import com.example.shopv2.wastes.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping("/api/test/product")
    public List<ProductResponse> getProductByName1(@RequestParam String name){
        return productService.getProductByName1(name);
    }

}

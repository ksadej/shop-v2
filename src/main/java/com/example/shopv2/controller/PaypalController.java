package com.example.shopv2.controller;

import com.example.shopv2.service.order.payment.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaypalController {

   private final PaypalService paypalService;

   @Autowired
    public PaypalController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @PostMapping("/api/orders")
    public Object createOrder(){
        return paypalService.createOrder();
    }
}

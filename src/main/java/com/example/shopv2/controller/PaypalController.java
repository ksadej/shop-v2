package com.example.shopv2.controller;

import com.example.shopv2.service.order.payment.PaypalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/api/orders/{orderId}/capture")
    public void capturePayment(@PathVariable("orderId") String orderId){
       paypalService.capturePayment(orderId);
    }
}

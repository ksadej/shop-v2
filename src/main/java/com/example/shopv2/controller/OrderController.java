package com.example.shopv2.controller;

import com.example.shopv2.model.Order;
import com.example.shopv2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/save")
    public void saveOrder(@RequestBody Order order){
        orderService.saveOrder(order);
    }
}

package com.example.shopv2.controller;

import com.example.shopv2.model.Orders;
import com.example.shopv2.service.OrdersService;
import com.example.shopv2.service.dto.OrdersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/order/save")
    public void saveOrder(@RequestBody OrdersDTO orders){
        ordersService.createSummary(orders);
    }

    @GetMapping("/order/get")
    public List<Orders> get(){
        return ordersService.getAll();
    }
}

package com.example.shopv2.controller;

import com.example.shopv2.service.order.OrdersService;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.dto.OrdersSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class OrdersController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/order/add")
    public OrdersSummaryDTO addOrder(@RequestBody OrdersDTO orders){
        return ordersService.createSummary(orders);
    }

    @PostMapping("/order/save/total")
    public OrdersSummaryDTO saveTotalOrder(@RequestBody OrdersDTO orders) throws IOException {
        return ordersService.finalSummary(orders);
    }

    @GetMapping("/order/list")
    public List<OrdersDTO> getOrdersList(){
        return ordersService.getOrders();
    }
}

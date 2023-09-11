package com.example.shopv2.controller;

import com.example.shopv2.model.Orders;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.order.OrdersStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersStatusController {

    private final OrdersStatusService ordersStatusService;

    @Autowired
    public OrdersStatusController(OrdersStatusService ordersStatusService) {
        this.ordersStatusService = ordersStatusService;
    }

    @GetMapping("/status/list")
    public List<OrdersDTO> getOrdersList(){
        return ordersStatusService.listOfOrdersToDo();
    }

    @GetMapping("/status/list/tst")
    public List<Orders> getOrdersList2(){
        return ordersStatusService.getAll();
    }
}

package com.example.shopv2.controller;

import com.example.shopv2.model.Orders;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.order.OrdersStatusService;
import com.example.shopv2.service.order.orderstatus.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdersStatusController {

    private final OrdersStatusService ordersStatusService;
    private final PaymentService paymentService;
    @Autowired
    public OrdersStatusController(OrdersStatusService ordersStatusService, PaymentService paymentService) {
        this.ordersStatusService = ordersStatusService;
        this.paymentService = paymentService;
    }

    @GetMapping("/status/list")
    public List<OrdersDTO> getOrdersList(){
        return ordersStatusService.orderStatus();
    }

    @PostMapping("/status/confirm")
    public Orders confirmStatusPayment(@RequestBody Orders orders){
        return paymentService.confirmStatus(orders);
    }

}

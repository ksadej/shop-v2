package com.example.shopv2.service.order.delivery;

import com.example.shopv2.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService  {
    private final OrdersRepository ordersRepository;

    @Autowired
    public DeliveryService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }



}

package com.example.shopv2.service;

import com.example.shopv2.model.Order;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.OrderRepository;
import com.example.shopv2.service.user.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserLogService userLogService;
    @Autowired
    public OrderService(OrderRepository orderRepository, UserLogService userLogService) {
        this.orderRepository = orderRepository;
        this.userLogService = userLogService;
    }

    public void saveOrder(Order order){
        UserEntity user = userLogService.loggedUser();
        order.setUserEntity(user);
        orderRepository.save(order);
    }
}

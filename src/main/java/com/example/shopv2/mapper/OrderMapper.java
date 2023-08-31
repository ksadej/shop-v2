package com.example.shopv2.mapper;

import com.example.shopv2.model.Order;
import com.example.shopv2.service.dto.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order requestToEntity(OrderDTO orderDTO){
        return Order
                .builder()
                .build();
    }

    public OrderDTO entityToResponse(Order order){
        return OrderDTO
                .builder()
                .build();
    }
}

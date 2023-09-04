package com.example.shopv2.mapper;

import com.example.shopv2.model.Orders;
import com.example.shopv2.model.enums.OrdersStatus;
import com.example.shopv2.service.dto.OrdersDTO;
import org.springframework.stereotype.Component;

@Component
public class OrdersMapper {

    public Orders requestToEntity(OrdersDTO ordersDTO){
        return Orders
                .builder()
                .ordersStatus(OrdersStatus.valueOf(ordersDTO.getOrdersStatus().name().toUpperCase()))
                .basketId(ordersDTO.getBasketId())
                .dataAdded(ordersDTO.getDataAdded())
                .build();
    }

    public OrdersDTO entityToResponse(Orders orders){
        return OrdersDTO
                .builder()
                .build();
    }
}

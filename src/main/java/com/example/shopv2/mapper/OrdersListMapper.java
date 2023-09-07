package com.example.shopv2.mapper;

import com.example.shopv2.model.OrdersList;
import com.example.shopv2.service.dto.OrdersListDTO;
import org.springframework.stereotype.Component;

@Component
public class OrdersListMapper {

    public static OrdersList mapToRow(OrdersList ordersList){
        return OrdersList
                .builder()
                .dataAdded(ordersList.getDataAdded())
                .orderId(ordersList.getOrderId())
                .productId(ordersList.getProductId())
                .recipesApiId(ordersList.getRecipesApiId())
                .build();
    }

    public static OrdersList mapToRow2(OrdersListDTO ordersListDTO){
        return OrdersList
                .builder()
                .dataAdded(ordersListDTO.getDataAdded())
                .orderId(ordersListDTO.getOrderId())
                .productId(ordersListDTO.getProductId())
                .recipesApiId(ordersListDTO.getRecipesApiId())
                .build();
    }

}

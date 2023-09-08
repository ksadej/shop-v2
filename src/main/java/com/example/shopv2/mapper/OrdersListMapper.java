package com.example.shopv2.mapper;

import com.example.shopv2.model.OrdersList;
import com.example.shopv2.service.dto.OrdersListDTO;
import org.springframework.stereotype.Component;

@Component
public class OrdersListMapper {

    public static OrdersListDTO entityToRequest(OrdersList ordersList){
        return OrdersListDTO
                .builder()
                .dataAdded(ordersList.getDataAdded())
                .ingredientId(ordersList.getIngredientId())
                .recipesApiId(ordersList.getRecipesApiId())
                .build();
    }

    public OrdersList mapToRow(OrdersList ordersList){
        return OrdersList
                .builder()
                .dataAdded(ordersList.getDataAdded())
                .ingredientId(ordersList.getIngredientId())
                .recipesApiId(ordersList.getRecipesApiId())
                .build();
    }

    public static OrdersList mapToRow2(OrdersListDTO ordersListDTO){
        return OrdersList
                .builder()
                .dataAdded(ordersListDTO.getDataAdded())
                .ingredientId(ordersListDTO.getIngredientId())
                .recipesApiId(ordersListDTO.getRecipesApiId())
                .build();
    }

}

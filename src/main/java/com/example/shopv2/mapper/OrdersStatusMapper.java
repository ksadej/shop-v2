package com.example.shopv2.mapper;

import com.example.shopv2.model.OrdersStatus;
import com.example.shopv2.service.dto.OrdersStatusDTO;
import org.springframework.stereotype.Component;

@Component
public class OrdersStatusMapper {

    public static OrdersStatusDTO entityToRequest(OrdersStatus ordersStatus){
        return OrdersStatusDTO
                .builder()
                .addedBy(ordersStatus.getAddedBy())
                .statusAddedAt(ordersStatus.getStatusAddedAt())
                .ordersStatus(ordersStatus.getOrdersStatus())
                .build();
    }
}

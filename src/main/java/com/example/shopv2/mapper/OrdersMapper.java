package com.example.shopv2.mapper;

import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersList;
import com.example.shopv2.model.Shipment;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.model.enums.ShipmentType;
import com.example.shopv2.service.OrdersService;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.dto.OrdersSummaryDTO;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class OrdersMapper {

    public Orders requestToEntity(OrdersDTO ordersDTO, UserEntity userEntity){
        return Orders
                .builder()
                .basketId(ordersDTO.getBasketId())
                .dataAdded(ordersDTO.getDataAdded())
                .ordersStatus(OrderStatusType.NEW)
                .shipmentType(ShipmentType.valueOf(ordersDTO.getShipmentType().toUpperCase()))
                .userEntity(userEntity)
                .build();
    }

    public OrdersSummaryDTO totalSummaryOrder(Orders newOrder){
        return OrdersSummaryDTO
                .builder()
                .shipmentType(String.valueOf(newOrder.getShipmentType()))
                .ordersStatus(String.valueOf(newOrder.getOrdersStatus()))
                .placeDate(OffsetDateTime.now())
                .build();
    }



}

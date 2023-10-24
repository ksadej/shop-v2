package com.example.shopv2.mapper;

import com.example.shopv2.model.Orders;
import com.example.shopv2.model.OrdersStatus;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.model.enums.PaymentType;
import com.example.shopv2.model.enums.ShipmentType;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.dto.OrdersStatusDTO;
import com.example.shopv2.service.dto.OrdersSummaryDTO;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@Component
public class OrdersMapper {

    public Orders requestToEntity(OrdersDTO ordersDTO, UserEntity userEntity){
        return Orders
                .builder()
                .basketId(ordersDTO.getBasketId())
                .dataAdded(ordersDTO.getDataAdded())
                .ordersStatus(OrderStatusType.NEW)
                .userEntity(userEntity)
                .build();
    }

    public OrdersDTO entityToRequest(Orders orders, UserEntity userEntity){
        return OrdersDTO
                .builder()
                .basketId(orders.getBasketId())
                .dataAdded(orders.getDataAdded())
                .userId(userEntity.getId())
                .ordersLists(orders.getOrdersLists()
                        .stream()
                        .map(OrdersListMapper::entityToRequest)
                        .collect(Collectors.toList()))
                .shipmentType(String.valueOf(orders.getShipmentType()))
                .paymentType(String.valueOf(orders.getPaymentType()))
                .build();
    }

    public OrdersDTO entityToRequest2(Orders orders, UserEntity userEntity){
        return OrdersDTO
                .builder()
                .basketId(orders.getBasketId())
                .dataAdded(orders.getDataAdded())
                .userId(userEntity.getId())
                .shipmentType(String.valueOf(orders.getShipmentType()))
                .paymentType(String.valueOf(orders.getPaymentType()))
                .ordersLists(orders.getOrdersLists()
                        .stream()
                        .map(OrdersListMapper::entityToRequest)
                        .collect(Collectors.toList()))
                .build();
    }

    public OrdersSummaryDTO totalSummaryOrder(Orders newOrder){
        return OrdersSummaryDTO
                .builder()
                .shipmentType(String.valueOf(newOrder.getShipmentType()))
                .payment(String.valueOf(newOrder.getPaymentType()))
                .ordersStatus(String.valueOf(newOrder.getOrdersStatus()))
                .placeDate(OffsetDateTime.now())
                .shipmentBy(null)
                .order(null)
                .destination(null)
                .build();
    }

    public OrdersDTO orderForWarehouse(Orders orders){
        return OrdersDTO
                .builder()
                .shipmentType(String.valueOf(orders.getShipmentType()))
                .ordersLists(orders.getOrdersLists()
                        .stream()
                        .map(OrdersListMapper::entityToRequest)
                        .collect(Collectors.toList()))
                .userId(orders.getId())
                .build();
    }

}

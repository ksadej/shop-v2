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
                .paymentType(PaymentType.valueOf(ordersDTO.getPaymentType()))
                .ordersStatus(OrderStatusType.NEW)
                .shipmentType(ShipmentType.valueOf(ordersDTO.getShipmentType().toUpperCase()))
                .userEntity(userEntity)
                .build();
    }

    public OrdersDTO entityToRequest(Orders orders, UserEntity userEntity){
        return OrdersDTO
                .builder()
                .basketId(orders.getBasketId())
                .dataAdded(orders.getDataAdded())
                .paymentType(String.valueOf(orders.getPaymentType()))
                .ordersStatusList(orders.getOrdersStatusList()
                        .stream()
                        .map(OrdersStatusMapper::entityToRequest)
                        .collect(Collectors.toList()))
                .shipmentType(String.valueOf(orders.getShipmentType()))
                .userId(userEntity.getId())
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

}

package com.example.shopv2.mapper;

import com.example.shopv2.model.Orders;
import com.example.shopv2.model.Shipment;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.model.enums.OrdersStatus;
import com.example.shopv2.model.enums.ShipmentType;
import com.example.shopv2.repository.OrdersRepository;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.dto.OrdersDTO;
import com.example.shopv2.service.user.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersMapper {

    public Orders requestToEntity(OrdersDTO ordersDTO, UserEntity userEntity){
        return Orders
                .builder()
                .basketId(ordersDTO.getBasketId())
                .dataAdded(ordersDTO.getDataAdded())
                .ordersStatus(OrdersStatus.NEW)
                .shipmentType(ShipmentType.valueOf(ordersDTO.getShipmentType().toUpperCase()))
                .userEntity(userEntity)
                .totalValue(ordersDTO.getTotalValue())
                .build();
    }

    public OrdersDTO entityToResponse(Orders orders){
        return OrdersDTO
                .builder()
                .build();
    }

}

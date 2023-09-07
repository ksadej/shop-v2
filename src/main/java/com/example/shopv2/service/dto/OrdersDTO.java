package com.example.shopv2.service.dto;

import com.example.shopv2.model.OrdersList;
import com.example.shopv2.model.Shipment;
import com.example.shopv2.model.enums.OrdersStatus;
import com.example.shopv2.model.enums.ShipmentType;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersDTO {

    private Long id;
    private OffsetDateTime dataAdded;
    private OrdersStatus ordersStatus;
    private Long userId;
    private Long basketId;
    private String shipmentType;
    private Double totalValue;
    private List<OrdersListDTO> ordersLists;
}

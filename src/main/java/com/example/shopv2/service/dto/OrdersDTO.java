package com.example.shopv2.service.dto;

import com.example.shopv2.model.Shipment;
import com.example.shopv2.model.enums.OrdersStatus;
import com.example.shopv2.model.enums.ShipmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.OffsetDateTime;

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

}

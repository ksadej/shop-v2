package com.example.shopv2.service.dto;

import com.example.shopv2.model.Payment;
import com.example.shopv2.model.Shipment;
import com.example.shopv2.model.enums.OrdersStatus;
import com.example.shopv2.model.enums.ShipmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersSummaryDTO {

    private Long id;
    private OffsetDateTime placeDate;
    private Double totalValue;
    private String ordersStatus;
    private String shipmentType;
}

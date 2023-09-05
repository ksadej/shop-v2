package com.example.shopv2.service.dto;

import com.example.shopv2.model.Payment;
import com.example.shopv2.model.enums.OrdersStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersSummary {

    private Long id;
    private OffsetDateTime placeDate;
    private BigDecimal totalValue;
    private OrdersStatus ordersStatus;
    private Payment payment;
}

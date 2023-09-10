package com.example.shopv2.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

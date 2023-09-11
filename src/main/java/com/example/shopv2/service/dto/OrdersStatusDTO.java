package com.example.shopv2.service.dto;

import com.example.shopv2.model.enums.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersStatusDTO {

    private Long id;
    private OffsetDateTime statusAddedAt;
    @Enumerated(EnumType.STRING)
    private OrderStatusType ordersStatus;
    private String addedBy;
}

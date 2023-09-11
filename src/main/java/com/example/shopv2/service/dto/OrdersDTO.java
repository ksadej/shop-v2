package com.example.shopv2.service.dto;

import com.example.shopv2.model.OrdersStatus;
import com.example.shopv2.model.enums.OrderStatusType;
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
    private List<OrdersStatusDTO> ordersStatusList;
    private Long userId;
    private Long basketId;
    private String shipmentType;
    private List<OrdersListDTO> ordersLists;
}

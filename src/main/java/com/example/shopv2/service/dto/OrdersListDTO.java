package com.example.shopv2.service.dto;

import com.example.shopv2.model.OrdersList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdersListDTO {

    private Long id;
    private OffsetDateTime dataAdded;
    private Long orderId;
    private Long productId;
    private Long recipesApiId;
}

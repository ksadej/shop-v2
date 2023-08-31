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
public class OrderDTO {

    private Long id;
    private OffsetDateTime dataAdded;
    private String oderStatus;
    private Long userId;
    private Long basketId;
}

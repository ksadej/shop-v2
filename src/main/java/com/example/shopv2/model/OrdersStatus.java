package com.example.shopv2.model;

import com.example.shopv2.model.enums.OrderStatusType;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "ORDERS_STATUS")
public class OrdersStatus {

    @Id
    @Column(name = "ORDER_STATUS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime statusAddedAt;
    @Enumerated(EnumType.STRING)
    private OrderStatusType ordersStatus;
    private String addedBy;
    private String transactionId;
}

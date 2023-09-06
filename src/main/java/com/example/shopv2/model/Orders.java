package com.example.shopv2.model;

import com.example.shopv2.model.enums.OrdersStatus;
import com.example.shopv2.model.enums.ShipmentType;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime dataAdded;
    @Enumerated(EnumType.STRING)
    private OrdersStatus ordersStatus;
    private Long basketId;
    @ManyToOne
    @JoinColumn(name = "USER_ENTITY_ID")
    private UserEntity userEntity;
    @Enumerated(EnumType.STRING)
    private ShipmentType shipmentType;
    private Double totalValue;

}

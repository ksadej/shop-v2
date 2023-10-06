package com.example.shopv2.model;

import com.example.shopv2.model.enums.OrderStatusType;
import com.example.shopv2.model.enums.PaymentType;
import com.example.shopv2.model.enums.ShipmentType;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;


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
    private OrderStatusType ordersStatus;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_STATUS_ID_ORDER_ID")
    private List<OrdersStatus> ordersStatusList;
    private Long basketId;
    @ManyToOne
    @JoinColumn(name = "USER_ENTITY_ID")
    private UserEntity userEntity;
    private ShipmentType shipmentType;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="ORDER_LIST_ID_ORDER_ID")
    private List<OrdersList> ordersLists;
    private PaymentType paymentType;
}

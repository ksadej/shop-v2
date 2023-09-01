package com.example.shopv2.model;

import com.example.shopv2.model.enums.OderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "ORDER")
public class Order {

    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime dataAdded;
    private OderStatus oderStatus;
    private Long basketId;
    @ManyToOne
    @JoinColumn(name = "USER_ENTITY_ID")
    private UserEntity userEntity;
}

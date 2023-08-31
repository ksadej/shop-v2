package com.example.shopv2.model;

import com.example.shopv2.model.enums.OderStatus;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    private Long id;
    private OffsetDateTime dataAdded;
    private OderStatus oderStatus;
    @ManyToOne
    @JoinColumn(name = "USER_ENTITY_ID")
    private UserEntity userEntity;
    private Long basketId;
}

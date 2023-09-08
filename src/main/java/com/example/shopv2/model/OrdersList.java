package com.example.shopv2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "ORDERS_LIST")
public class OrdersList {

    @Id
    @Column(name = "ORDER_LIST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime dataAdded;
    private Long ingredientId;
    private Long recipesApiId;
}

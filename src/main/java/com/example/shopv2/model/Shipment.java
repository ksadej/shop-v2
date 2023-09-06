package com.example.shopv2.model;

import com.example.shopv2.model.enums.ShipmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "SHIPMENT")
public class Shipment {

    @Id
    @Column(name = "SHIPMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    @Enumerated(EnumType.STRING)
    private ShipmentType shipmentType;
}

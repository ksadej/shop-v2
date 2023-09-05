package com.example.shopv2.model;

import com.example.shopv2.model.enums.ShimpentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "SHIPMENT")
public class Shipment {

    @Id
    @Column(name = "RECIPES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ShimpentType type;
}

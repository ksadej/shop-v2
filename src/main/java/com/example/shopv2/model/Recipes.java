package com.example.shopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "RECIPES")
public class Recipes {

    @Id
    @Column(name = "RECIPES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="BASKET_ID")
    private Basket basket;

    private Integer idRecipesAPI;
}

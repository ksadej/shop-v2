package com.example.shopv2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "NUTRITION")
public class Nutrition {

    @Id
    @Column(name = "NUTRITION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name ="INGREDIENT_ID")
    private Ingredient ingredient;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name ="BASKET_ID")
    private Basket basket;

    private String name;
    private double amount;
    private String unit;
    private double percentOfDailyNeeds;
    private Integer idIngredientAPI;

}

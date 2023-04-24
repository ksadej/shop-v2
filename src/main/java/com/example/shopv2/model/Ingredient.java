package com.example.shopv2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "INGREDIENT")
public class Ingredient {

    @Id
    @Column(name = "INGREDIENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name ="CARD_ID")

    private Card card;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private List<Nutrition> nutrition = new ArrayList<>();


    private Integer idIngredientAPI;
    private String aisle;
    private String image;
    private String consistency;
    private String name;
    private String nameClean;
    private String original;
    private String originalName;
    private double amount;
    private String unit;
}

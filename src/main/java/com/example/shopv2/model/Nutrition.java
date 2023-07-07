package com.example.shopv2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Override
    public String toString() {
        return "Nutrition{" +
                "id=" + id +
                ", ingredient=" + ingredient +
                ", basket=" + basket +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", percentOfDailyNeeds=" + percentOfDailyNeeds +
                ", idIngredientAPI=" + idIngredientAPI +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutrition nutrition = (Nutrition) o;
        return Double.compare(nutrition.amount, amount) == 0 && Double.compare(nutrition.percentOfDailyNeeds, percentOfDailyNeeds) == 0 && Objects.equals(id, nutrition.id) && Objects.equals(ingredient, nutrition.ingredient) && Objects.equals(basket, nutrition.basket) && Objects.equals(name, nutrition.name) && Objects.equals(unit, nutrition.unit) && Objects.equals(idIngredientAPI, nutrition.idIngredientAPI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ingredient, basket, name, amount, unit, percentOfDailyNeeds, idIngredientAPI);
    }
}

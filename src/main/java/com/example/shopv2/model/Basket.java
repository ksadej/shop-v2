package com.example.shopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "BASKET")
public class Basket {

    @Id
    @Column(name = "BASKET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    public String name;
    public double calories;
    public double serving_size_g;
    public double fat_total_g;
    public double fat_saturated_g;
    public double protein_g;
    public int sodium_mg;
    public int potassium_mg;
    public int cholesterol_mg;
    public double carbohydrates_total_g;
    public double fiber_g;
    public double sugar_g;

    public Basket(double calories) {
        this.calories = calories;
    }
}

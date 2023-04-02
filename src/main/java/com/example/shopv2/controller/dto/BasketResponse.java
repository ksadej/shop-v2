package com.example.shopv2.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketResponse {

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

}

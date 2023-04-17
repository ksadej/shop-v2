package com.example.shopv2.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NutritionNutrientResponse {
    public String name;
    public double amount;
    public String unit;
    public double percentOfDailyNeeds;

    public List<NutritionNutrientResponse> nutrient;
}

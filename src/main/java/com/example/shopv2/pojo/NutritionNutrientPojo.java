package com.example.shopv2.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NutritionNutrientPojo {
    public String name;
    public double amount;
    public String unit;
    public double percentOfDailyNeeds;

    public List<NutritionNutrientPojo> nutrient;
}

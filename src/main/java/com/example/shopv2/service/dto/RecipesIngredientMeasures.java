package com.example.shopv2.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecipesIngredientMeasures {
    public RecipesIngredientMeasuresMetricResponse metric;
}

package com.example.shopv2.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecipesIngredientMeasuresMetricPojo {
    public double amount;
    public String unitShort;
    public String unitLong;
}

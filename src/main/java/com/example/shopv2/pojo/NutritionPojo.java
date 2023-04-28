package com.example.shopv2.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NutritionPojo {
    public ArrayList<NutritionNutrientPojo> nutrients;

}

package com.example.shopv2.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NutritionResponse {
    public ArrayList<NutritionNutrientResponse> nutrients;

}

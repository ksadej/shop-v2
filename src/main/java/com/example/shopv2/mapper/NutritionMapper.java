package com.example.shopv2.mapper;

import com.example.shopv2.model.Basket;
import com.example.shopv2.model.Nutrition;
import com.example.shopv2.pojo.NutritionNutrientPojo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NutritionMapper {

    public Nutrition requestToEntity(NutritionNutrientPojo nutritionNutrientPojo){
        return Nutrition
                .builder()
                .name(nutritionNutrientPojo.getName())
                .amount(nutritionNutrientPojo.getAmount())
                .percentOfDailyNeeds(nutritionNutrientPojo.getPercentOfDailyNeeds())
                .unit(nutritionNutrientPojo.getUnit())
                .build();
    }

    public Nutrition requestToEntity(Basket basket){
        return Nutrition
                .builder()
                .name(basket.getNutritionList().stream().map(x->x.getName()).toString())
                .amount(basket.getNutritionList().stream().map(x->x.getAmount()).mapToDouble(Double::doubleValue).sum())
                .percentOfDailyNeeds(basket.getNutritionList().stream().map(x->x.getPercentOfDailyNeeds()).mapToDouble(Double::doubleValue).sum())
                .unit(basket.getNutritionList().stream().map(x->x.getUnit()).toString())
                .build();
    }
}

package com.example.shopv2.mapper;

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
}

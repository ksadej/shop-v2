package com.example.shopv2.mapper;

import com.example.shopv2.model.Nutrition;
import com.example.shopv2.pojo.NutritionNutrientPojo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NutritionMapper {

    public Nutrition nutritionPojoToNutrition(List<NutritionNutrientPojo> nutritionNutrientPojo, int i){

        return Nutrition
                .builder()
                .name(nutritionNutrientPojo.stream().map(x -> x.getName()).toList().get(i))
                .amount(nutritionNutrientPojo.stream().map(x -> x.getAmount()).toList().get(i))
                .percentOfDailyNeeds(nutritionNutrientPojo.stream().map(x -> x.getAmount()).toList().get(i))
                .unit(nutritionNutrientPojo.stream().map(x -> x.getUnit()).toList().get(i))
                .build();
    }
}

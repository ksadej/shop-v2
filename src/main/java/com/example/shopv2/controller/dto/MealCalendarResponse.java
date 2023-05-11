package com.example.shopv2.controller.dto;

import com.example.shopv2.model.enums.MealTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealCalendarResponse {

    private Long id;
    private Integer idRecipes;
    private String day;
    private MealTime time;
    private OffsetDateTime dataMeal;
}

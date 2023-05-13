package com.example.shopv2.controller.dto;

import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealCalendarRequest {

    private Long id;
    private Integer idRecipes;
    private Days day;
    private MealTime time;
    private OffsetDateTime dataMeal;

    public MealCalendarRequest(Integer idRecipes, Days day, MealTime time, OffsetDateTime dataMeal) {
        this.idRecipes = idRecipes;
        this.day = day;
        this.time = time;
        this.dataMeal = dataMeal;
    }
}

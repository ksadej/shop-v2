package com.example.shopv2.service.dto;

import com.example.shopv2.model.enums.Days;
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
public class MealCalendarDTO {

    private Long id;
    private Integer idRecipes;
    private Days day;
    private MealTime time;
    private OffsetDateTime dataMeal;

    public MealCalendarDTO(Integer idRecipes, Days day, MealTime time, OffsetDateTime dataMeal) {
        this.idRecipes = idRecipes;
        this.day = day;
        this.time = time;
        this.dataMeal = dataMeal;
    }

    public MealCalendarDTO(Days day, MealTime time) {
        this.day = day;
        this.time = time;
    }
}

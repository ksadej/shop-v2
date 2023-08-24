package com.example.shopv2.mapper;

import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.service.dto.MealCalendarDTO;
import org.springframework.stereotype.Component;

@Component
public class MealCalendarMapper{

    public MealCalendar requestToEntity(MealCalendarDTO mealCalendarDTO){
        return MealCalendar
                .builder()
                .idRecipes(mealCalendarDTO.getIdRecipes())
                .day(Days.valueOf(mealCalendarDTO.getDay().name().toUpperCase()))
                .time(mealCalendarDTO.getTime())
                .dataMeal(mealCalendarDTO.getDataMeal())
                .build();
    }

    public com.example.shopv2.service.dto.MealCalendarDTO entityToResponse(MealCalendar mealCalendar){
        return com.example.shopv2.service.dto.MealCalendarDTO
                .builder()
                .id(mealCalendar.getId())
                .idRecipes(mealCalendar.getIdRecipes())
                .day(mealCalendar.getDay())
                .time(mealCalendar.getTime())
                .dataMeal(mealCalendar.getDataMeal())
                .build();
    }
}

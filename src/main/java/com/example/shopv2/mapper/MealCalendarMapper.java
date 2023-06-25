package com.example.shopv2.mapper;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MealCalendarMapper implements MealCalendarMapperImpl{

    @Override
    public MealCalendar requestToEntity(MealCalendarRequest mealCalendarRequest){
        return MealCalendar
                .builder()
                .idRecipes(mealCalendarRequest.getIdRecipes())
                .day(Days.valueOf(mealCalendarRequest.getDay().name().toUpperCase()))
                .time(mealCalendarRequest.getTime())
                .dataMeal(mealCalendarRequest.getDataMeal())
                .build();
    }

    @Override
    public MealCalendarResponse entityToResponse(MealCalendar mealCalendar){
        return MealCalendarResponse
                .builder()
                .id(mealCalendar.getId())
                .idRecipes(mealCalendar.getIdRecipes())
                .day(mealCalendar.getDay())
                .time(mealCalendar.getTime())
                .dataMeal(mealCalendar.getDataMeal())
                .build();
    }
}

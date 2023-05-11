package com.example.shopv2.mapper;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.model.MealCalendar;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MealCalendarMapper {

    public MealCalendarResponse entityToResponse(List<MealCalendar> mealCalendar, int i){
        return MealCalendarResponse
                .builder()
                .id(mealCalendar.get(i).getId())
                .idRecipes(mealCalendar.get(i).getIdRecipes())
                .day(mealCalendar.get(i).getDay())
                .time(mealCalendar.get(i).getTime())
                .dataMeal(mealCalendar.get(i).getDataMeal())
                .build();
    }

    public MealCalendar requestToEntity(MealCalendarRequest mealCalendarRequest){
        return MealCalendar
                .builder()
                .idRecipes(mealCalendarRequest.getIdRecipes())
                .day(mealCalendarRequest.getDay())
                .time(mealCalendarRequest.getTime())
                .dataMeal(mealCalendarRequest.getDataMeal())
                .build();
    }
}

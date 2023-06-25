package com.example.shopv2.mapper;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.model.MealCalendar;

public interface MealCalendarMapperImpl {

    Object requestToEntity(MealCalendarRequest mealCalendarRequest);
    Object entityToResponse(MealCalendar mealCalendar);
}

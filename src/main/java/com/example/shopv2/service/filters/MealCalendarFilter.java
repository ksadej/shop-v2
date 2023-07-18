package com.example.shopv2.service.filters;

import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import com.example.shopv2.repository.MealCalendarRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MealCalendarFilter extends AbstractFilter{

    private final MealCalendarRepository mealCalendarRepository;

    public MealCalendarFilter(MealCalendarRepository mealCalendarRepository) {
        this.mealCalendarRepository = mealCalendarRepository;
    }
    

    @Override
    protected List<MealCalendar> filterByDayAndTime(Days day, MealTime time) {
        return null;
    }
}

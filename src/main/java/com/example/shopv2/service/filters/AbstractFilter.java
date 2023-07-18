package com.example.shopv2.service.filters;

import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AbstractFilter {

    public List<MealCalendar> getAllByFilter(Map<String, String> filter) {
        return Collections.emptyList();
    }

    protected abstract List<MealCalendar> filterByDayAndTime(Days day, MealTime time);
}

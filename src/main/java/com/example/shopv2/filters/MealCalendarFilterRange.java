package com.example.shopv2.filters;

import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.repository.MealCalendarRepository;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class MealCalendarFilterRange extends FilterRangeAbstract{

    private final MealCalendarRepository mealCalendarRepository;

    public MealCalendarFilterRange(MealCalendarRepository mealCalendarRepository) {
        this.mealCalendarRepository = mealCalendarRepository;
    }

    @Override
    protected String getFilterName() {
        return "MealCalendar";
    }

    @Override
    protected List<MealCalendar> getAllEntityBetweenDate(OffsetDateTime fromDate, OffsetDateTime toDate) {
        return mealCalendarRepository.findAllByBetweenDate(fromDate, toDate);
    }
}

package com.example.shopv2.repository;

import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealCalendarRepository extends JpaRepository<MealCalendar, Long> {

    public List<MealCalendar> findByDayAndTime(Days day, MealTime time);
}

package com.example.shopv2.repository;

import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface MealCalendarRepository extends JpaRepository<MealCalendar, Long> {

    List<MealCalendar> findByDayAndTime(Days day, MealTime time);

    @Query("SELECT e FROM MealCalendar e WHERE e.dataMeal >= :dataFrom AND e.dataMeal <= :dataTo")
    List<MealCalendar> findAllByBetweenDate(OffsetDateTime dataFrom, OffsetDateTime dataTo);

    List<MealCalendar> findAllByDay(Days day);

    List<MealCalendar> findAllByTime(MealTime mealTime);
}

package com.example.shopv2.controller;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import com.example.shopv2.repository.MealCalendarRepository;
import com.example.shopv2.service.MealCalendarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MealCalendarController {

    private final MealCalendarService mealCalendarService;
    private final MealCalendarRepository mealCalendarRepository;

    @Autowired
    public MealCalendarController(MealCalendarService mealCalendarService, MealCalendarRepository mealCalendarRepository) {
        this.mealCalendarService = mealCalendarService;
        this.mealCalendarRepository = mealCalendarRepository;
    }


    @PostMapping(path = "/calendar")
    public MealCalendar saveMealCalendar(@RequestBody MealCalendarRequest mealCalendarRequest){
        return mealCalendarService.saveMealCalendar(mealCalendarRequest);
    }

    @DeleteMapping(path = "/calendar/{id}")
    public void deleteMealCalendar(@PathVariable Long id){
        mealCalendarService.deleteMealCalendar(id);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path ="/calendar/filter/{day}/{time}")
    public List<MealCalendarResponse> getByDayAndTime(@PathVariable Days day, @PathVariable MealTime time){
        return mealCalendarService.filterByDayAndTime(day, time);
    }

//    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping(path ="/calendar")
    public List<MealCalendarResponse> getCalendar(){
        return mealCalendarService.getCalendar();
    }

    @GetMapping(path = "/calendar/filter")
    public List<MealCalendarResponse> filterMealsBetweenDate(@RequestParam String fromDate, @RequestParam String toDate){
        return mealCalendarService.filterMealsBetweenDate(fromDate, toDate);
    }

    @GetMapping(path = "/calendar/filter/date")
    public List<MealCalendarResponse> filterMealsDate(@RequestParam Map<String, String> filter){
        return mealCalendarService.getAllFilteredMeals(filter);
    }

    @GetMapping(path = "/calendar/filter/day/{day}")
    public List<MealCalendarResponse> filterByDays(@PathVariable String day){
        return mealCalendarService.filterByDays(day);
    }

    @GetMapping(path = "/calendar/filter/time/{mealTime}")
    public List<MealCalendarResponse> filterByTime(@PathVariable String mealTime){
        return mealCalendarService.filterByTimeOfDay(mealTime);
    }
}

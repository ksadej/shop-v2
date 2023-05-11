package com.example.shopv2.controller;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.MealTime;
import com.example.shopv2.service.MealCalendarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MealCalendarController {

    private final MealCalendarService mealCalendarService;

    @Autowired
    public MealCalendarController(MealCalendarService mealCalendarService) {
        this.mealCalendarService = mealCalendarService;
    }

    @PostMapping(path = "/calendar")
    public void saveMealCalendar(@RequestBody MealCalendarRequest mealCalendarRequest){
        mealCalendarService.saveMealCalendar(mealCalendarRequest);
    }

    @GetMapping(path ="/calendar/{day}/{time}")
    public List<MealCalendarResponse> getByDayAndTime(@PathVariable String day, @PathVariable MealTime time){
        return mealCalendarService.getByDayAndTime(day, time);
    }

    @GetMapping(path ="/calendar")
    public List<MealCalendarResponse> getCalendar(){
        return mealCalendarService.getCalendar();
    }
}

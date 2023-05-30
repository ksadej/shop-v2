package com.example.shopv2.controller;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import com.example.shopv2.repository.MealCalendarRepository;
import com.example.shopv2.service.MealCalendarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path ="/calendar/{day}/{time}")
    public List<MealCalendarResponse> getByDayAndTime(@PathVariable Days day, @PathVariable MealTime time){
        return mealCalendarService.getByDayAndTime(day, time);
    }

    @GetMapping(path ="/calendar")
    public List<MealCalendarResponse> getCalendar(){
        return mealCalendarService.getCalendar();
    }

}

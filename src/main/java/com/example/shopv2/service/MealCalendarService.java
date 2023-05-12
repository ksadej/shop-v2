package com.example.shopv2.service;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.mapper.MealCalendarMapper;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import com.example.shopv2.repository.MealCalendarRepository;
import com.example.shopv2.validator.MealCalendarValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealCalendarService {

    private final MealCalendarRepository mealCalendarRepository;
    private final MealCalendarMapper mealCalendarMapper;
    private final MealCalendarValidator mealCalendarValidator;

    @Autowired
    public MealCalendarService(MealCalendarRepository mealCalendarRepository, MealCalendarMapper mealCalendarMapper, MealCalendarValidator mealCalendarValidator) {
        this.mealCalendarRepository = mealCalendarRepository;
        this.mealCalendarMapper = mealCalendarMapper;
        this.mealCalendarValidator = mealCalendarValidator;
    }

    public void saveMealCalendar(MealCalendarRequest mealCalendarRequest){
        mealCalendarValidator.saveMealCalendarValidator(mealCalendarRequest);

        MealCalendar mealCalendar = mealCalendarMapper.requestToEntity(mealCalendarRequest);
        mealCalendarRepository.save(mealCalendar);
    }

    public void saveMealCalendarTest(MealCalendarRequest mealCalendarRequest){
        mealCalendarValidator.saveMealCalendarValidator(mealCalendarRequest);


        MealCalendar mealCalendar = mealCalendarMapper.requestToEntity(mealCalendarRequest);
        mealCalendarRepository.save(mealCalendar);
    }

    public List<MealCalendarResponse> getByDayAndTime(String day, MealTime time){
        List<MealCalendar> mealCalendars = mealCalendarRepository.findByDayAndTime(day, time);

        List<MealCalendarResponse> calendarResponses = new ArrayList<>();

        for(int i=0; i<mealCalendars.size(); i++) {
            calendarResponses.add(mealCalendarMapper.entityToResponse(mealCalendars, i)) ;
        }

        return calendarResponses;
    }



    public List<MealCalendarResponse> getCalendar(){
        List<MealCalendar> mealCalendars = mealCalendarRepository.findAll();

        List<MealCalendarResponse> calendarResponses = new ArrayList<>();

        for(int i=0; i<mealCalendars.size(); i++) {
            calendarResponses.add(mealCalendarMapper.entityToResponse(mealCalendars, i)) ;
        }

        return calendarResponses;
    }
}

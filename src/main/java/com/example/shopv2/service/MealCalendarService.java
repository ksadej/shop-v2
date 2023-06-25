package com.example.shopv2.service;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.mapper.MealCalendarMapper;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import com.example.shopv2.repository.MealCalendarRepository;
import com.example.shopv2.validator.MealCalendarValidator;
import com.example.shopv2.validator.enums.MealCalendarEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealCalendarService {

    private final MealCalendarRepository mealCalendarRepository;
    private final MealCalendarMapper mealCalendarMapper;
    private final MealCalendarValidator mealCalendarValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(MealCalendarService.class.getName());

    @Autowired
    public MealCalendarService(MealCalendarRepository mealCalendarRepository,
                               MealCalendarMapper mealCalendarMapper,
                               MealCalendarValidator mealCalendarValidator) {
        this.mealCalendarRepository = mealCalendarRepository;
        this.mealCalendarMapper = mealCalendarMapper;
        this.mealCalendarValidator = mealCalendarValidator;
    }

    public MealCalendar saveMealCalendar(MealCalendarRequest mealCalendarRequest){
        LOGGER.info("Saved MealCalendarRequest by MealCalendarService");
        LOGGER.debug("MealCalendarRequest: "+mealCalendarRequest);
        mealCalendarValidator.saveMealCalendarValidator(mealCalendarRequest);

//        mealCalendarValidator.saveMealCalendarValidator2(mealCalendarRequest);
        MealCalendar mealCalendar = mealCalendarMapper.requestToEntity(mealCalendarRequest);
        LOGGER.debug("Data saved: "+mealCalendar);
        return mealCalendarRepository.save(mealCalendar);
    }

    public List<MealCalendarResponse> getByDayAndTime(Days day, MealTime time){
        LOGGER.info("Method getByDayAndTime");
        LOGGER.debug("Days: "+day+" Meal time: "+time);
        MealCalendarResponse mealCalendarResponse = new MealCalendarResponse(day, time);
        mealCalendarValidator.getByDayAndTimeValidator(mealCalendarResponse);
        List<MealCalendar> mealCalendars = mealCalendarRepository.findByDayAndTime(day, time);

        List<MealCalendarResponse> calendarResponses = new ArrayList<>();

        for(int i=0; i<mealCalendars.size(); i++) {
            calendarResponses.add(mealCalendarMapper.entityToResponse(mealCalendars, i)) ;
        }
        LOGGER.debug("Response from getByDayAndTime: "+calendarResponses);
        return calendarResponses;
    }

    public List<MealCalendarResponse> getCalendar(){
        LOGGER.info("Method getCalendar");
        List<MealCalendar> mealCalendars = mealCalendarRepository.findAll();

        List<MealCalendarResponse> calendarResponses = new ArrayList<>();

        for(int i=0; i<mealCalendars.size(); i++) {
            calendarResponses.add(mealCalendarMapper.entityToResponse(mealCalendars, i));
        }

        LOGGER.debug("Calendar returned: "+calendarResponses);
        return calendarResponses;
    }

    public void deleteMealCalendar(Long id) {
        LOGGER.info("Method deleteMealCalendar");
        LOGGER.info("Deleted element with id: "+id);
        mealCalendarValidator.deleteMealCalendarValidator(id);
        mealCalendarRepository.deleteById(id);
    }

    public List<MealCalendarResponse> getMealsBetweenDate(String fromDate, String toDate){
        final String dateSuffix = "T00:00:00.001Z";
        final OffsetDateTime fData = OffsetDateTime.parse(fromDate + dateSuffix);
        final OffsetDateTime tDate = OffsetDateTime.parse(toDate + dateSuffix);

        List<MealCalendar>  mealCalendars= mealCalendarRepository.findAllByBetweenDate(fData, tDate);
        List<MealCalendarResponse> calendarResponses = new ArrayList<>();

//        for(int i=0; i< mealCalendars.size(); i++){
//            calendarResponses.add(mealCalendarMapper.entityToResponse(mealCalendars, i));
//        }
//        return calendarResponses;

        return mealCalendarRepository.findAllByBetweenDate(fData, tDate)
                .stream()
                .map(MealCalendarMapper -> mealCalendarMapper.entityToResponse(MealCalendarMapper))
                .collect(Collectors.toList());

    }
}

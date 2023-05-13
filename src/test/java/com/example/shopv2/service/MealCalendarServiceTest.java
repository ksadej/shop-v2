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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MealCalendarServiceTest {

    private MealCalendarRepository mealCalendarRepository;
    private MealCalendarService mealCalendarService;
    MealCalendarValidator mealCalendarValidator = new MealCalendarValidator();

    @BeforeEach
    public void setup(){
        mealCalendarRepository = mock(MealCalendarRepository.class);
        MealCalendarMapper mealCalendarMapper = mock(MealCalendarMapper.class);
        mealCalendarService = new MealCalendarService(mealCalendarRepository, mealCalendarMapper, mealCalendarValidator);
    }

    @Test
    void saveMealCalendar_saveObject_saved() {
        //given
        MealCalendarRequest mealCalendarRequest =
                new MealCalendarRequest(12, Days.FRIDAY, MealTime.EVENING, OffsetDateTime.now().plusHours(1));

        //when
        mealCalendarService.saveMealCalendar(mealCalendarRequest);

        //then
        ArgumentCaptor<MealCalendar> argumentCaptor = ArgumentCaptor.forClass(MealCalendar.class);
        verify(mealCalendarRepository).save(argumentCaptor.capture());
    }

    @Test
    void saveMealCalendar_whenOneOfTheValueIsNull_mealCalendarException(){
        //given
        MealCalendarRequest mealCalendarRequest1 = new MealCalendarRequest(null, Days.FRIDAY, MealTime.EVENING, OffsetDateTime.now());
        MealCalendarRequest mealCalendarRequest2 = new MealCalendarRequest(12, null, MealTime.EVENING, OffsetDateTime.now());
        MealCalendarRequest mealCalendarRequest3 = new MealCalendarRequest(12, Days.FRIDAY, null, OffsetDateTime.now());
        MealCalendarRequest mealCalendarRequest4 = new MealCalendarRequest(12, Days.FRIDAY, MealTime.EVENING, null);

        //when//then
        assertThrows(MealCalendarException.class, () -> mealCalendarService.saveMealCalendar(mealCalendarRequest1));
        assertThrows(MealCalendarException.class, () -> mealCalendarService.saveMealCalendar(mealCalendarRequest2));
        assertThrows(MealCalendarException.class, () -> mealCalendarService.saveMealCalendar(mealCalendarRequest3));
        assertThrows(MealCalendarException.class, () -> mealCalendarService.saveMealCalendar(mealCalendarRequest4));
    }

    @Test
    void saveMealCalendar_whenTheValuesAreNull_mealCalendarException(){
        //given
        MealCalendarRequest mealCalendarRequest = new MealCalendarRequest();

        //when//then
        assertThrows(MealCalendarException.class, () -> mealCalendarService.saveMealCalendar(mealCalendarRequest));

    }
    @Test
    void getByDayAndTime_MealCalendarResponse_returnMealCalendarResponseObject() {
        //given
        Days d = Days.MONDAY;
        MealTime m =  MealTime.MORNING;

        //when

//        mealCalendarService.getByDayAndTime(d,m);
        //then

    }

}

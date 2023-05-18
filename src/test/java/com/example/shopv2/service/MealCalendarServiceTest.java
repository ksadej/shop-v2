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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MealCalendarServiceTest {

    private MealCalendarRepository mealCalendarRepository;
    private MealCalendarService mealCalendarService;
    MealCalendarValidator mealCalendarValidator = new MealCalendarValidator(mealCalendarRepository);

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

        MealCalendar expectedData = new MealCalendar(12, Days.FRIDAY, MealTime.EVENING, OffsetDateTime.now().plusHours(1));
        List<MealCalendar> mealCalendarList = new ArrayList<>();
        mealCalendarList.add(expectedData);

        when(mealCalendarRepository.findByDayAndTime(d,m)).thenReturn(mealCalendarList);

        //when
        mealCalendarService.getByDayAndTime(d,m);

        //then
        verify(mealCalendarRepository).findByDayAndTime(d,m);
    }

    @Test
    void getByDayAndTime_getWhenDayOrTimeIsNull_returnMealCalendarException() {
        //given
        Days d = Days.MONDAY;
        MealTime m =  MealTime.MORNING;

        //when//then
        assertThrows(MealCalendarException.class, () -> mealCalendarService.getByDayAndTime(null,m));
        assertThrows(MealCalendarException.class, () -> mealCalendarService.getByDayAndTime(d,null));
    }

    @Test
    void deleteMealCalendar_whereIdIsNullOrLessThanZeroOrEqual_mealCalendarException(){
        //given
        final Long id1 = 0L;
        final Long id2 =-2L;
        final Long id3 =null;

        //when//then
        assertThrows(MealCalendarException.class, () -> mealCalendarService.deleteMealCalendar(id1));
        assertThrows(MealCalendarException.class, () -> mealCalendarService.deleteMealCalendar(id2));
        assertThrows(MealCalendarException.class, () -> mealCalendarService.deleteMealCalendar(id3));
    }

    @Test
    void deleteMealCalendar_whereIdDoNotExists_mealCalendarException(){
        //given
        MealCalendar mealCalendar = new MealCalendar(7L, 12, Days.FRIDAY, MealTime.EVENING, OffsetDateTime.now().plusHours(1));
        when(mealCalendarRepository.existsById(mealCalendar.getId())).thenReturn(true);
    }

    @Test
    void deleteMealCalendar_deleteById_deleted() {
        //given
        MealCalendar mealCalendar = new MealCalendar(7L, 12, Days.FRIDAY, MealTime.EVENING, OffsetDateTime.now().plusHours(1));
        when(mealCalendarRepository.existsById(mealCalendar.getId())).thenReturn(true);
        //when
        mealCalendarRepository.deleteById(mealCalendar.getId());

        //then
        verify(mealCalendarRepository, times(1)).deleteById(mealCalendar.getId());
    }

    @Test
    void getCalendar_gettingMealCalendarObject_checkingSizeOfMealCalendarList(){
        //given
        MealCalendar mealCalendarOne = new MealCalendar(7L, 12, Days.FRIDAY, MealTime.EVENING, OffsetDateTime.now().plusHours(1));
        List<MealCalendar> calendarResponses = List.of(mealCalendarOne);
        when(mealCalendarRepository.findAll()).thenReturn(calendarResponses);

        //when
        List<MealCalendarResponse> result = mealCalendarService.getCalendar();

        //then
       assertThat(result)
               .hasSize(1);
    }

}

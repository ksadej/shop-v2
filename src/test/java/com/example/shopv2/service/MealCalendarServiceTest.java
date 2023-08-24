package com.example.shopv2.service;

import com.example.shopv2.exceptions.MealCalendarException;
import com.example.shopv2.mapper.MealCalendarMapper;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import com.example.shopv2.repository.MealCalendarRepository;
import com.example.shopv2.service.dto.MealCalendarDTO;
import com.example.shopv2.validator.MealCalendarValidator;
import com.example.shopv2.validator.ParametersValidatorFactory.MealCalendarParametersValidator;
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
    private MealCalendarMapper mealCalendarMapper;
    MealCalendarValidator mealCalendarValidator = new MealCalendarValidator(mealCalendarRepository);
    MealCalendarParametersValidator mealCalendarParametersValidator = new MealCalendarParametersValidator();

    @BeforeEach
    public void setup(){
        mealCalendarRepository = mock(MealCalendarRepository.class);
        mealCalendarMapper = mock(MealCalendarMapper.class);
        mealCalendarService = new MealCalendarService(mealCalendarRepository,
                mealCalendarMapper,
                mealCalendarValidator,
                mealCalendarParametersValidator, null);
    }

    @Test
    void saveMealCalendar_saveObject_saved() {
        //given
        MealCalendarDTO mealCalendarDTO =
                new MealCalendarDTO(12, Days.FRIDAY, MealTime.EVENING, OffsetDateTime.now().plusHours(1));

        //when
        mealCalendarService.saveMealCalendar(mealCalendarDTO);

        //then
        ArgumentCaptor<MealCalendar> argumentCaptor = ArgumentCaptor.forClass(MealCalendar.class);
        verify(mealCalendarRepository).save(argumentCaptor.capture());
    }

    @Test
    void saveMealCalendar_whenOneOfTheValueIsNull_mealCalendarException(){
        //given
        MealCalendarDTO mealCalendarRequest1 = new MealCalendarDTO(null, Days.FRIDAY, MealTime.EVENING, OffsetDateTime.now());
        MealCalendarDTO mealCalendarRequest2 = new MealCalendarDTO(12, null, MealTime.EVENING, OffsetDateTime.now());
        MealCalendarDTO mealCalendarRequest3 = new MealCalendarDTO(12, Days.FRIDAY, null, OffsetDateTime.now());
        MealCalendarDTO mealCalendarRequest4 = new MealCalendarDTO(12, Days.FRIDAY, MealTime.EVENING, null);

        //when//then
        assertThrows(MealCalendarException.class, () -> mealCalendarService.saveMealCalendar(mealCalendarRequest1));
        assertThrows(MealCalendarException.class, () -> mealCalendarService.saveMealCalendar(mealCalendarRequest2));
        assertThrows(MealCalendarException.class, () -> mealCalendarService.saveMealCalendar(mealCalendarRequest3));
        assertThrows(MealCalendarException.class, () -> mealCalendarService.saveMealCalendar(mealCalendarRequest4));
    }

    @Test
    void saveMealCalendar_whenTheValuesAreNull_mealCalendarException(){
        //given
        MealCalendarDTO mealCalendarRequest = new MealCalendarDTO();

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
        mealCalendarService.filterByDayAndTime(d,m);

        //then
        verify(mealCalendarRepository).findByDayAndTime(d,m);
    }

    @Test
    void getByDayAndTime_getWhenDayOrTimeIsNull_returnMealCalendarException() {
        //given
        Days d = Days.MONDAY;
        MealTime m =  MealTime.MORNING;

        //when//then
        assertThrows(MealCalendarException.class, () -> mealCalendarService.filterByDayAndTime(null,m));
        assertThrows(MealCalendarException.class, () -> mealCalendarService.filterByDayAndTime(d,null));
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
        List<com.example.shopv2.service.dto.MealCalendarDTO> result = mealCalendarService.getCalendar();

        //then
       assertThat(result)
               .hasSize(1);
    }

    @Test
    void getMealsBetweenDate_getDataBetweenDate_returnData(){
        //given
        final String fromDate = "2023-01-04";
        final String toDate = "2023-01-05";
        final String dateSuffix = "T00:00:00.001Z";
        final OffsetDateTime fDate = OffsetDateTime.parse(fromDate + dateSuffix);
        final OffsetDateTime tDate = OffsetDateTime.parse(toDate + dateSuffix);
        final MealCalendar mealCalendarOne =
                new MealCalendar(7L, 12, Days.FRIDAY, MealTime.EVENING, OffsetDateTime.parse("2023-01-05T10:12:30+01:00"));
        when(mealCalendarRepository.findAllByBetweenDate(fDate, tDate)).thenReturn(List.of(mealCalendarOne));

        //when
        mealCalendarService.filterMealsBetweenDate(fromDate, toDate);

        //then
        verify(mealCalendarRepository, times(1)).findAllByBetweenDate(fDate, tDate);
    }

}

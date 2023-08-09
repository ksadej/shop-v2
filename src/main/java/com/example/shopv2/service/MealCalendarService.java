package com.example.shopv2.service;

import com.example.shopv2.controller.dto.MealCalendarRequest;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.filters.FilterRangeAbstract;
import com.example.shopv2.filters.MealCalendarFilterRange;
import com.example.shopv2.mapper.MealCalendarMapper;
import com.example.shopv2.model.MealCalendar;
import com.example.shopv2.model.enums.Days;
import com.example.shopv2.model.enums.MealTime;
import com.example.shopv2.repository.MealCalendarRepository;
import com.example.shopv2.service.user.UserLogService;
import com.example.shopv2.validator.MealCalendarValidator;
import com.example.shopv2.validator.ParametersValidatorFactory.MealCalendarParametersValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MealCalendarService {

    private final MealCalendarRepository mealCalendarRepository;
    private final MealCalendarMapper mealCalendarMapper;
    private final MealCalendarValidator mealCalendarValidator;
    private final MealCalendarParametersValidator mealCalendarParametersValidator;
    private static final Logger LOGGER = LoggerFactory.getLogger(MealCalendarService.class.getName());
    private final FilterRangeAbstract<MealCalendar> filterRangeAbstract;

    @Autowired
    public MealCalendarService(MealCalendarRepository mealCalendarRepository,
                               MealCalendarMapper mealCalendarMapper,
                               MealCalendarValidator mealCalendarValidator,
                               MealCalendarParametersValidator mealCalendarParametersValidator,
                               MealCalendarFilterRange mealCalendarFilterRange) {
        this.mealCalendarRepository = mealCalendarRepository;
        this.mealCalendarMapper = mealCalendarMapper;
        this.mealCalendarValidator = mealCalendarValidator;
        this.mealCalendarParametersValidator = mealCalendarParametersValidator;
        this.filterRangeAbstract = mealCalendarFilterRange;

    }

    public MealCalendar saveMealCalendar(MealCalendarRequest mealCalendarRequest){
        LOGGER.info("Saved MealCalendarRequest by MealCalendarService");
        LOGGER.debug("MealCalendarRequest: "+mealCalendarRequest);
        mealCalendarValidator.saveMealCalendarValidator(mealCalendarRequest);

        MealCalendar mealCalendar = mealCalendarMapper.requestToEntity(mealCalendarRequest);
        LOGGER.debug("Data saved: "+mealCalendar);
        return mealCalendarRepository.save(mealCalendar);
    }


    public List<MealCalendarResponse> getCalendar(){
        LOGGER.info("Method getCalendar");

        return mealCalendarRepository.findAll()
                .stream()
                .map(MealCalendarMapper -> mealCalendarMapper.entityToResponse(MealCalendarMapper))
                .collect(Collectors.toList());
    }

    public void deleteMealCalendar(Long id) {
        LOGGER.info("Method deleteMealCalendar");
        LOGGER.info("Deleted element with id: "+id);
        mealCalendarValidator.deleteMealCalendarValidator(id);
        mealCalendarRepository.deleteById(id);
    }

    public List<MealCalendarResponse> filterByDayAndTime(Days day, MealTime time){
        LOGGER.info("Method getByDayAndTime");
        LOGGER.debug("Days: "+day+" Meal time: "+time);
        mealCalendarValidator.getByDayAndTimeValidator(new MealCalendarResponse(day, time));

        return mealCalendarRepository.findByDayAndTime(day, time)
                .stream()
                .map(MealCalendarMapper -> mealCalendarMapper.entityToResponse(MealCalendarMapper))
                .collect(Collectors.toList());
    }

    public List<MealCalendarResponse> filterByDays(String day){
        return  mealCalendarRepository.findAllByDay(Days.valueOf(day))
                .stream()
                .map(MealCalendarMapper -> mealCalendarMapper.entityToResponse(MealCalendarMapper))
                .collect(Collectors.toList());
    }

    public List<MealCalendarResponse> filterByTimeOfDay(String mealTime){
        return mealCalendarRepository.findAllByTime(MealTime.valueOf(mealTime))
                .stream()
                .map(MealCalendarMapper -> mealCalendarMapper.entityToResponse(MealCalendarMapper))
                .collect(Collectors.toList());
    }

    public List<MealCalendarResponse> filterByRecipesName(String name){
        return null;
    }

    public List<MealCalendarResponse> filterMealsBetweenDate(String fromDate, String toDate){

        final String dateSuffix = "T00:00:00.001Z";
        final OffsetDateTime fData = OffsetDateTime.parse(fromDate + dateSuffix);
        final OffsetDateTime tDate = OffsetDateTime.parse(toDate + dateSuffix);

        return mealCalendarRepository.findAllByBetweenDate(fData, tDate)
                .stream()
                .map(MealCalendarMapper -> mealCalendarMapper.entityToResponse(MealCalendarMapper))
                .collect(Collectors.toList());
    }

    public List<MealCalendarResponse> getAllFilteredMeals(Map<String, String> filter){

        return filterRangeAbstract.getAllByFilters(filter)
                .stream()
                .map(MealCalendarMapper -> mealCalendarMapper.entityToResponse(MealCalendarMapper))
                .collect(Collectors.toList());
    }

}

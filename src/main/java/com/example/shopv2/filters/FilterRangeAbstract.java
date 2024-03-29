package com.example.shopv2.filters;

import com.example.shopv2.validator.ParametersValidatorFactory.BasketParametersValidator;
import com.example.shopv2.validator.ParametersValidatorFactory.MealCalendarParametersValidator;
import com.example.shopv2.validator.enums.MonthsEnum;
import com.example.shopv2.validator.enums.FilterParametersEnum;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class FilterRangeAbstract<T> {

    @Autowired
    private MealCalendarParametersValidator mealCalendarParametersValidator;

    @Autowired
    private BasketParametersValidator basketParametersValidator;

    private final static String DATE_SUFFIX = "T00:00:00.001Z";

    public List<T> getAllByFilters(Map<String, String> filter){

        //klasa która implementuję metodę getFilterName() będzie zwracała odpowiedni string MealCalendar lub BasketFilter
        //dzięki tym metodom klasa która je implementuje będdzie infomowała jakim jest filtrem
        //dzięki temu zostanie wywołany odpowiedni validator
        if("MealCalendar".equals(getFilterName())){
            mealCalendarParametersValidator.validateFilter(filter);
        }

        if("BasketFilter".equals(getFilterName())){
            basketParametersValidator.validateFilter(filter);
        }

        if(isFilterForFromToDate(filter)) {
            String fromDate = filter.get(FilterParametersEnum.FROM_DATE.getKey());
            String toDate = filter.get(FilterParametersEnum.TO_DATE.getKey());
            return getAllEntityBetweenDate(parseDatetoOffsetDateTime(fromDate), parseDatetoOffsetDateTime(toDate));

        } else if (isFilterForMonthYear(filter)) {
            MonthsEnum month = MonthsEnum.valueOf(filter.get(FilterParametersEnum.MONTH.getKey()).toUpperCase());
            String year = filter.get(FilterParametersEnum.YEAR.getKey());
            return getAllForMonthInYear(month, year);
        }
        return Collections.emptyList();
    }

    protected abstract String getFilterName();

    private boolean isFilterForFromToDate(Map<String, String> filter) {
        return filter.containsKey(FilterParametersEnum.FROM_DATE.getKey())
                && filter.containsKey(FilterParametersEnum.TO_DATE.getKey());
    }

    private boolean isFilterForMonthYear(Map<String, String> filter) {
        return filter.containsKey(FilterParametersEnum.YEAR.getKey())
                && filter.containsKey(FilterParametersEnum.MONTH.getKey());
    }

    private List<T> getAllForMonthInYear(MonthsEnum month, String year) {
        String from = month.getFirstDayForYear(year);
        String to = month.getLastDayForYear(year);

        return getAllEntityBetweenDate(parseDatetoOffsetDateTime(from), parseDatetoOffsetDateTime(to));
    }

    private OffsetDateTime parseDatetoOffsetDateTime(String date){
        return OffsetDateTime.parse(date + DATE_SUFFIX);
    }

    protected abstract List<T> getAllEntityBetweenDate(OffsetDateTime fromDate, OffsetDateTime toDate);
}

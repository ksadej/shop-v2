package com.example.shopv2.service.download;

import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.model.Basket;
import com.example.shopv2.model.MealCalendar;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealCalendarDownloadBuilder {

    private final String SEPARATOR = ";";
    public StringBuffer prepareBuffer(List<MealCalendarResponse> basketList){
        StringBuffer stringBuffer =
                new StringBuffer("Id"+SEPARATOR+"Recipes id"+SEPARATOR+"Time"+SEPARATOR+"Day"+SEPARATOR+"Meal date");
        basketList.forEach(x -> stringBuffer
                .append("\n")
                .append(x.getId())
                .append(SEPARATOR)
                .append(x.getIdRecipes())
                .append(SEPARATOR)
                .append(x.getTime())
                .append(SEPARATOR)
                .append(x.getDay())
                .append(SEPARATOR)
                .append(x.getDataMeal())
        );
        return stringBuffer;
    }
}

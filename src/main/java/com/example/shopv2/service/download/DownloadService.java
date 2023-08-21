package com.example.shopv2.service.download;

import com.example.shopv2.controller.dto.BasketResponse;
import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.mapper.BasketMapper;
import com.example.shopv2.model.Basket;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.MealCalendarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DownloadService {

    private final BasketDownloadBuilder basketDownloadBuilder;
    private final ResponseDownloadedService responseDownloadedService;
    private final MealCalendarDownloadBuilder mealCalendarDownloadBuilder;
    private final BasketService basketService;
    private final MealCalendarService mealCalendarService;
    private final BasketMapper basketMapper;

    public void fileToDownload(HttpServletResponse response, DownloadTypes downloadTypes, Map<String, String> filter){
        switch(downloadTypes){
            case BASKET:
                fileToDownloadForBasket(response, filter);
                break;

            case MEALCALENDAR:
                fileToDownloadForMealCalendar(response);
                break;
        }
    }

    public void fileToDownloadForBasket(HttpServletResponse response, Map<String, String> filter){
        List<BasketResponse> cardByUser = getBasket(filter);
        StringBuffer stringBuffer = basketDownloadBuilder.prepareBuffer(cardByUser);
        responseDownloadedService.toResponse(response, stringBuffer);
    }

    public List<BasketResponse> getBasket(Map<String, String> filter){
        if(Objects.isNull(filter)){
            return basketService.getBasketByUser()
                    .stream()
                    .map(BasketMapper -> basketMapper.entityToResponse(BasketMapper))
                    .collect(Collectors.toList());
        }
        return basketService.getAllFilteredBasket(filter);
    }

    public void fileToDownloadForMealCalendar(HttpServletResponse response){
        List<MealCalendarResponse> calendar = mealCalendarService.getCalendar();
        StringBuffer stringBuffer = mealCalendarDownloadBuilder.prepareBuffer(calendar);
        responseDownloadedService.toResponse(response, stringBuffer);
    }
}

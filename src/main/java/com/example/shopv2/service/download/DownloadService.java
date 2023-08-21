package com.example.shopv2.service.download;

import com.example.shopv2.controller.dto.MealCalendarResponse;
import com.example.shopv2.model.Basket;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.MealCalendarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@AllArgsConstructor
public class DownloadService {

    private final BasketDownloadBuilder basketDownloadBuilder;
    private final ResponseDownloadedService responseDownloadedService;
    private final MealCalendarDownloadBuilder mealCalendarDownloadBuilder;
    private final BasketService basketService;
    private final MealCalendarService mealCalendarService;

    public void fileToDownload(HttpServletResponse response, DownloadTypes downloadTypes){
        switch(downloadTypes){
            case BASKET:
                fileToDownloadForBasket(response);
                break;

            case MEALCALENDAR:
                fileToDownloadForMealCalendar(response);
                break;
        }
    }

    public void fileToDownloadForBasket(HttpServletResponse response){
        List<Basket> cardByUser = basketService.getBasketByUser();
        StringBuffer stringBuffer = basketDownloadBuilder.prepareBuffer(cardByUser);
        responseDownloadedService.toResponse(response, stringBuffer);
    }

    public void fileToDownloadForMealCalendar(HttpServletResponse response){
        List<MealCalendarResponse> calendar = mealCalendarService.getCalendar();
        StringBuffer stringBuffer = mealCalendarDownloadBuilder.prepareBuffer(calendar);
        responseDownloadedService.toResponse(response, stringBuffer);
    }
}

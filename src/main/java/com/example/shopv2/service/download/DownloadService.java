package com.example.shopv2.service.download;

import com.example.shopv2.service.dto.BasketDTO;
import com.example.shopv2.service.dto.MealCalendarDTO;
import com.example.shopv2.mapper.BasketMapper;
import com.example.shopv2.service.BasketService;
import com.example.shopv2.service.MealCalendarService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DownloadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadService.class.getName());
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
        List<BasketDTO> cardByUser = getBasket(filter);
        StringBuffer stringBuffer = basketDownloadBuilder.prepareBuffer(cardByUser);
        responseDownloadedService.toResponse(response, stringBuffer);
    }

    public List<BasketDTO> getBasket(Map<String, String> filter){
        if(Objects.isNull(filter)){
            List<BasketDTO> collect = basketService.getBasketByUser()
                    .stream()
                    .map(BasketMapper -> basketMapper.entityToResponse(BasketMapper))
                    .collect(Collectors.toList());
            LOGGER.info("Object is not null and returned: "+collect);

            return collect;
        }
        List<BasketDTO> allFilteredBasket = basketService.getAllFilteredBasket(filter);
        LOGGER.info("Object is null and returned: "+allFilteredBasket);
        return allFilteredBasket;
    }

    public void fileToDownloadForMealCalendar(HttpServletResponse response){
        List<MealCalendarDTO> calendar = mealCalendarService.getCalendar();
        StringBuffer stringBuffer = mealCalendarDownloadBuilder.prepareBuffer(calendar);
        responseDownloadedService.toResponse(response, stringBuffer);
    }
}

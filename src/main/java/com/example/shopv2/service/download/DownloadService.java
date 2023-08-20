package com.example.shopv2.service.download;

import com.example.shopv2.model.Basket;
import com.example.shopv2.service.BasketService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class DownloadService {

    private final BasketDownloadBuilder basketDownloadBuilder;
    private final ResponseDownloadedService responseDownloadedService;
    private final BasketService basketService;

    public DownloadService(BasketDownloadBuilder basketDownloadBuilder,
                           ResponseDownloadedService responseDownloadedService,
                           BasketService basketService) {
        this.basketDownloadBuilder = basketDownloadBuilder;
        this.responseDownloadedService = responseDownloadedService;
        this.basketService = basketService;
    }

    public void fileToDownload(HttpServletResponse response){
        List<Basket> cardByUser = basketService.getBasketByUser();
        StringBuffer stringBuffer = basketDownloadBuilder.prepareBuffer(cardByUser);
        responseDownloadedService.toResponse(response, stringBuffer);
    }
}

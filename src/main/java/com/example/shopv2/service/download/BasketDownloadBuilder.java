package com.example.shopv2.service.download;

import com.example.shopv2.model.Basket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketDownloadBuilder {

    private final String SEPARATOR = ";";
    public StringBuffer prepareBuffer(List<Basket> basketList){
        StringBuffer stringBuffer = new StringBuffer("Id"+SEPARATOR+"Date added"+SEPARATOR+"Added By");
        basketList.forEach(x -> stringBuffer
                        .append("\n")
                        .append(x.getId())
                        .append(SEPARATOR)
                        .append(x.getDataAdded())
                        .append(SEPARATOR)
                        .append(x.getIdUser())
        );
        return stringBuffer;
    }
}

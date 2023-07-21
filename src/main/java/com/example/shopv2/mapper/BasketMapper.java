package com.example.shopv2.mapper;

import com.example.shopv2.controller.dto.BasketRequest;
import com.example.shopv2.controller.dto.BasketResponse;
import com.example.shopv2.model.Basket;
import org.springframework.stereotype.Component;

@Component
public class BasketMapper implements BasketMapperImpl{

    @Override
    public Basket requestToEntity(BasketRequest basketRequest) {
        return null;
    }

    @Override
    public BasketResponse entityToResponse(Basket basket) {
        return BasketResponse.
                builder()
                .id(basket.getId())
                .ingredients(basket.getIngredients())
                .recipes(basket.getRecipes())
                .nutritionList(basket.getNutritionList())
                .idUser(basket.getIdUser())
                .dataAdded(basket.getDataAdded())
                .build();
    }
}

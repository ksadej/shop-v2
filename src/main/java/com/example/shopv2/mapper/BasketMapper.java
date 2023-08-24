package com.example.shopv2.mapper;

import com.example.shopv2.service.dto.BasketDTO;
import com.example.shopv2.model.Basket;
import org.springframework.stereotype.Component;

@Component
public class BasketMapper{

    public Basket requestToEntity(BasketDTO basketDTO) {
        return null;
    }

    public BasketDTO entityToResponse(Basket basket) {
        return BasketDTO.
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

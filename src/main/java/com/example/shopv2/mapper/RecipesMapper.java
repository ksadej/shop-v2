package com.example.shopv2.mapper;

import com.example.shopv2.model.Recipes;
import com.example.shopv2.service.dto.RecipesDTO;
import org.springframework.stereotype.Component;

@Component
public class RecipesMapper {

    public RecipesDTO entityToDto(Recipes recipes){
        return RecipesDTO
                .builder()
                .idRecipesAPI(recipes.getIdRecipesAPI())
                .build();
    }
}

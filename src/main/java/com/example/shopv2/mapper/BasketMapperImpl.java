package com.example.shopv2.mapper;

import com.example.shopv2.controller.dto.BasketRequest;
import com.example.shopv2.model.Basket;


public interface BasketMapperImpl {

    Object requestToEntity(BasketRequest basketRequest);
    Object entityToResponse(Basket basket);
}

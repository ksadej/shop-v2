package com.example.shopv2.controller.dto;

import com.example.shopv2.model.Ingredient;
import com.example.shopv2.model.Nutrition;
import com.example.shopv2.model.Recipes;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketRequest {

    private Long id;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Recipes> recipes = new ArrayList<>();
    private List<Nutrition> nutritionList = new ArrayList<>();
    private Long idUser;
    private OffsetDateTime dataAdded;
}

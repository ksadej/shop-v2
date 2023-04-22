package com.example.shopv2.service;


import com.example.shopv2.model.Ingredient;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.service.dto.NutritionNutrientResponse;
import com.example.shopv2.service.dto.RecipesIngredientResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

    private  final IngredientRepository ingredientRepository;
    private final RestTemplate restTemplate;

    private final HttpHeaders httpHeaders;

    public IngredientService(IngredientRepository ingredientRepository, RestTemplate restTemplate, @Qualifier("recipesHeaders") HttpHeaders httpHeaders) {
        this.ingredientRepository = ingredientRepository;
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    //pobiera listę wartości odzywszczych składników na podstawie id składników
    public  ArrayList<NutritionNutrientResponse> getNutritionByIngredientId(Long id){
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<RecipesIngredientResponse> entity = restTemplate
                .exchange("https://api.spoonacular.com/food/ingredients/"+id+"/information?amount=1",
                        HttpMethod.GET,
                        httpEntity,
                        RecipesIngredientResponse.class);

        ArrayList<NutritionNutrientResponse> nutrient = entity.getBody().getNutrition().getNutrients();

        return nutrient;
    }

//    public NutritionNutrientResponse sumAllNutrientsByIngredientId(Long id){
//
//        ArrayList<NutritionNutrientResponse> nutrient = getNutritionByIngredientId(id);
//
//        NutritionNutrientResponse nutrientResponse = NutritionNutrientResponse
//                .builder()
//
//                .build();
//        return null;
//    }

//    public List<Ingredient> getAllIngredients(){
//        return ingredientRepository.findAll();
//    }
}

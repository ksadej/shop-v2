package com.example.shopv2.service;


import com.example.shopv2.model.Ingredient;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, RestTemplate restTemplate, @Qualifier("recipesHeaders") HttpHeaders httpHeaders) {
        this.ingredientRepository = ingredientRepository;
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }


    //pobiera składniki z przepisu na podstawie id przepisu
    public List<RecipesIngredientPojo> getIngredientByRecipesId(Integer id){
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
        System.out.println("Identyfikator: "+id);
        ResponseEntity<RecipesIngredientPojo> entity = restTemplate
                .exchange("https://api.spoonacular.com/recipes/"+id+"/information?includeNutrition=false",
                        HttpMethod.GET,
                        httpEntity,
                        RecipesIngredientPojo.class);
        System.out.println(entity.getBody().getExtendedIngredients());
        ArrayList<RecipesIngredientPojo> recipesIngredientResponses = entity.getBody().getExtendedIngredients();

        return recipesIngredientResponses;
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

    public List<Ingredient> getAllIngredients(){
        return ingredientRepository.findAll();
    }
}

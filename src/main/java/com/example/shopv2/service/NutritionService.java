package com.example.shopv2.service;

import com.example.shopv2.mapper.NutritionMapper;
import com.example.shopv2.model.Nutrition;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.NutritionRepository;
import com.example.shopv2.pojo.NutritionNutrientPojo;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.stream.Collectors;

@Service
public class NutritionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NutritionService.class.getName());

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final BasketRepository basketRepository;
    private final NutritionMapper nutritionMapper;
    private final NutritionRepository nutritionRepository;
    @Autowired
    public NutritionService(RestTemplate restTemplate,
                            @Qualifier("recipesHeaders") HttpHeaders httpHeaders,
                            BasketRepository basketRepository, NutritionMapper nutritionMapper, NutritionRepository nutritionRepository) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.basketRepository = basketRepository;
        this.nutritionMapper = nutritionMapper;
        this.nutritionRepository = nutritionRepository;
    }

    //pobiera listę wartości odzywszczych składników na podstawie id składników
    public ArrayList<NutritionNutrientPojo> getNutritionByIngredientId(Long id){
        LOGGER.info("Getting nutrition by ingredients id");
        LOGGER.debug("Ingredient id: "+id);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<RecipesIngredientPojo> entity = restTemplate
                .exchange("https://api.spoonacular.com/food/ingredients/"+id+"/information?amount=1",
                        HttpMethod.GET,
                        httpEntity,
                        RecipesIngredientPojo.class);

        ArrayList<NutritionNutrientPojo> nutrient = entity.getBody().getNutrition().getNutrients();
        LOGGER.debug("List of nutritions"+ entity.getBody().getNutrition().getNutrients());

        return nutrient;
    }

    public void saveNutritionByIngredientId(Long id){
        LOGGER.info("Saved nutrition by ingredients id");
        LOGGER.debug("Ingredient id: "+id);
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<RecipesIngredientPojo> entity = restTemplate
                .exchange("https://api.spoonacular.com/food/ingredients/"+id+"/information?amount=1",
                        HttpMethod.GET,
                        httpEntity,
                        RecipesIngredientPojo.class);

        ArrayList<NutritionNutrientPojo> nutrient = entity.getBody().getNutrition().getNutrients();

        for(int i=0; i<nutrient.size(); i++){
            Nutrition nutrition = Nutrition
                    .builder()
                    .name(nutrient.stream().map(x ->x.getName()).collect(Collectors.toList()).get(i))
                    .build();
            nutritionRepository.save(nutrition);
        }
        LOGGER.info("Nutrition saved");
    }
}

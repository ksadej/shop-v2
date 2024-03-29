package com.example.shopv2.service;

import com.example.shopv2.mapper.RecipesMapper;
import com.example.shopv2.model.Basket;
import com.example.shopv2.model.Recipes;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.pojo.RecipesPojo;
import com.example.shopv2.pojo.ResultPojo;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.service.dto.RecipesDTO;
import com.example.shopv2.service.user.UserLogService;
import com.example.shopv2.validator.RecipesValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipesService.class.getName());

    private final BasketRepository basketRepository;
    private final RestTemplate restTemplate;
    private final RecipesValidator recipesValidator;
    private final HttpHeaders httpHeaders;
    private final UserLogService userLogService;
    private final RecipesMapper recipesMapper;


    @Autowired
    public RecipesService(BasketRepository basketRepository, RestTemplate restTemplate,
                          @Qualifier("recipesHeaders") HttpHeaders httpHeaders,
                          RecipesValidator recipesValidator, UserLogService userLogService, RecipesMapper recipesMapper) {
        this.basketRepository = basketRepository;
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.recipesValidator = recipesValidator;
        this.userLogService = userLogService;
        this.recipesMapper = recipesMapper;
    }

    public List<RecipesPojo> getRecipesByType(String type){
        recipesValidator.getRecipesByTypeValidate(type);

        LOGGER.info("Getting recipes by typ");
        LOGGER.debug("Type of recipes: "+type);

        ArrayList<RecipesPojo> entity =
                Connection.externalApiConnectionGET(
                        "https://api.spoonacular.com/recipes/complexSearch?type=" + type,
                            ResultPojo.class).getResults();
        LOGGER.debug("List of recipes by type "+ entity);
        return entity;
    }

    public RecipesPojo getRecipesById(Integer id){
        LOGGER.info("Getting recipes by id");
        LOGGER.debug("ID of recipes: "+id);

        RecipesPojo rootResponses = Connection.externalApiConnectionGET(
                "https://api.spoonacular.com/recipes/" + id + "/information?includeNutrition=false",
                RecipesPojo.class);

        LOGGER.debug("Recipes by id: "+ rootResponses);
        return rootResponses;
    }

    public List<RecipesDTO> getRecipesByUserId(){
        UserEntity user = userLogService.loggedUser();
        List<Basket> basketList = basketRepository.findAllByUserEntity(user);

        List<Recipes> recipes = basketList
                .stream()
                .flatMap(x -> x.getRecipes().stream())
                .collect(Collectors.toList());

        List<RecipesDTO> recipesDTO = recipes
                .stream()
                .map(x -> recipesMapper.entityToDto(x))
                .collect(Collectors.toList());

        return recipesDTO;
    }

    //filtry: wyszukiwanie po ingredient, po nutrition, wyszukiwanie recepty o podobnych skladnikach
    //filtry: recepty do wyszukiwania w kategorii kaloryczności
    //filtr: w kategorii zdrowotnosci przepisu (healthScore)
    //filtr: w kategorii ceny
    //filtr: w kategorii: mięsnej, vege, itd
    //filtr: typ dania: lunch, main course itd
}

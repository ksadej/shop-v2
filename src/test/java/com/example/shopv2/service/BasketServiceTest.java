package com.example.shopv2.service;

import com.example.shopv2.model.Basket;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.model.Recipes;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import com.example.shopv2.pojo.RecipesPojo;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.NutritionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class BasketServiceTest {

    private BasketService basketService;
    private BasketRepository basketRepository;
    private RecipesService recipesService;
    private NutritionService nutritionService;
    private IngredientService ingredientService;
    private IngredientRepository ingredientRepository;
    private NutritionRepository nutritionRepository;
    @BeforeEach
    public void setup(){
        basketRepository = Mockito.mock(BasketRepository.class);
        recipesService = Mockito.mock(RecipesService.class);
        nutritionService = Mockito.mock(NutritionService.class);
        ingredientService = Mockito.mock(IngredientService.class);
        ingredientRepository = Mockito.mock(IngredientRepository.class);
        nutritionRepository = Mockito.mock(NutritionRepository.class);
        basketService = new BasketService(basketRepository, recipesService,
                nutritionService, ingredientService,
                ingredientRepository, nutritionRepository);
    }

    @Test
    void saveRecipesAndIngredientsByRecipesId_saveObject_saved(){
        //given
        RecipesIngredientPojo recipesIngredientPojo1 = RecipesIngredientPojo
                .builder()
                .name("name1")
                .build();

        List<RecipesIngredientPojo> recipesIngredientPojoList = new ArrayList<>();
        recipesIngredientPojoList.add(recipesIngredientPojo1);
        RecipesPojo recipesPojo = new RecipesPojo();

        Integer id = 22;
        when(ingredientService.getIngredientByRecipesId(id)).thenReturn(recipesIngredientPojoList);
        when(recipesService.getRecipesById(id)).thenReturn(recipesPojo);

        //when
        basketService.saveRecipesAndIngredientsByRecipesId(id);

        //then
        ArgumentCaptor<Basket> argumentCaptor1 = ArgumentCaptor.forClass(Basket.class);
        verify(basketRepository, times(2)).save(argumentCaptor1.capture());
    }


    @Test
    void saveAllByRecipesId_saveObject_saved(){
        //given
        RecipesIngredientPojo recipesIngredientPojo1 = RecipesIngredientPojo
                .builder()
                .name("name1")
                .build();

        List<RecipesIngredientPojo> recipesIngredientPojoList = new ArrayList<>();
        recipesIngredientPojoList.add(recipesIngredientPojo1);
        RecipesPojo recipesPojo = new RecipesPojo();
        Integer id = 22;

        when(ingredientService.getIngredientByRecipesId(id)).thenReturn(recipesIngredientPojoList);
        when(recipesService.getRecipesById(id)).thenReturn(recipesPojo);

        //when
        basketService.saveAllByRecipesId(id);

        //then
        ArgumentCaptor<Basket> argumentCaptor1 = ArgumentCaptor.forClass(Basket.class);
        verify(basketRepository, times(2)).save(argumentCaptor1.capture());
    }

    @Test
    void saveNutritionByIngredientId_saveObject_saved(){
        //given
        RecipesIngredientPojo recipesIngredientPojo = RecipesIngredientPojo
                .builder()
                .name("name1")
                .build();

        List<RecipesIngredientPojo> recipesIngredientPojoList = new ArrayList<>();
        recipesIngredientPojoList.add(recipesIngredientPojo);
        RecipesPojo recipesPojo = new RecipesPojo();
        Integer id = 22;

        when(ingredientService.getIngredientByRecipesId(id)).thenReturn(recipesIngredientPojoList);
        when(recipesService.getRecipesById(id)).thenReturn(recipesPojo);

        //when
        basketService.saveNutritionByIngredientId(id);

        //then
        ArgumentCaptor<Basket> argumentCaptor1 = ArgumentCaptor.forClass(Basket.class);
        verify(basketRepository, times(1)).save(argumentCaptor1.capture());
    }

}
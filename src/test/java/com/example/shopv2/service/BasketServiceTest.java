package com.example.shopv2.service;

import com.example.shopv2.exceptions.BasketException;
import com.example.shopv2.mapper.NutritionMapper;
import com.example.shopv2.model.Basket;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import com.example.shopv2.pojo.RecipesPojo;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.NutritionRepository;
import com.example.shopv2.validator.BasketValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BasketServiceTest {

    private BasketService basketService;
    private BasketRepository basketRepository;
    private RecipesService recipesService;
    private NutritionService nutritionService;
    private IngredientService ingredientService;
    private IngredientRepository ingredientRepository;
    private NutritionRepository nutritionRepository;
    private BasketValidator basketValidator = new BasketValidator();
    private NutritionMapper nutritionMapper = new NutritionMapper();

    @BeforeEach
    public void setup(){
        basketRepository = Mockito.mock(BasketRepository.class);
        recipesService = Mockito.mock(RecipesService.class);
        nutritionService = Mockito.mock(NutritionService.class);
        ingredientService = Mockito.mock(IngredientService.class);
        ingredientRepository = Mockito.mock(IngredientRepository.class);
        nutritionRepository = Mockito.mock(NutritionRepository.class);
//        basketValidator = Mockito.mock(BasketValidator.class);
        basketService = new BasketService(basketRepository, recipesService,
                nutritionService, ingredientService,
                ingredientRepository, nutritionRepository, basketValidator, nutritionMapper);
    }

    @Test
    void saveRecipesAndIngredientsByRecipesId_saveObject_saved(){
        //given
        //when
        basketService.saveRecipesAndIngredientsByRecipesId(2333);

        //then
        ArgumentCaptor<Basket> argumentCaptor = ArgumentCaptor.forClass(Basket.class);
        verify(basketRepository, times(1)).save(argumentCaptor.capture());
    }

    @Test
    void saveRecipesAndIngredientsByRecipesId_saveObject_saved2(){
        //given
        final Integer recipesId =1212;

        RecipesIngredientPojo recipesIngredientPojo1 = RecipesIngredientPojo
                .builder()
                .name("name1")
                .build();

        when(ingredientService.getIngredientByRecipesId(recipesId)).thenReturn(List.of(recipesIngredientPojo1));
        when(recipesService.getRecipesById(recipesId)).thenReturn(new RecipesPojo());

        //when
        basketService.saveRecipesAndIngredientsByRecipesId(recipesId);

        //then
        ArgumentCaptor<Basket> argumentCaptor = ArgumentCaptor.forClass(Basket.class);
        verify(basketRepository, times(1)).save(argumentCaptor.capture());
    }

    @Test
    void saveAllByRecipesId_saveObject_checkIfSaved(){
        //given
        when(ingredientService.getIngredientByRecipesId(anyInt())).thenReturn(List.of(new RecipesIngredientPojo()));
        when(recipesService.getRecipesById(anyInt())).thenReturn(new RecipesPojo());

        //when
        basketService.saveAllByRecipesId(anyInt());

        //then
        ArgumentCaptor<Basket> argumentCaptor1 = ArgumentCaptor.forClass(Basket.class);
        verify(basketRepository, times(1)).save(argumentCaptor1.capture());
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

    @Test
    void saveMethods_checkSearchedId_returnException(){
        //given//when//then
        assertThrows(BasketException.class, () -> basketService.saveAllByRecipesId(0));
        assertThrows(BasketException.class, () -> basketService.saveAllByRecipesId(-1));
        assertThrows(BasketException.class, () -> basketService.saveNutritionByIngredientId(0));
        assertThrows(BasketException.class, () -> basketService.saveNutritionByIngredientId(-32));
        assertThrows(BasketException.class, () -> basketService.saveRecipesAndIngredientsByRecipesId(0));
        assertThrows(BasketException.class, () -> basketService.saveRecipesAndIngredientsByRecipesId(-53));
    }
}
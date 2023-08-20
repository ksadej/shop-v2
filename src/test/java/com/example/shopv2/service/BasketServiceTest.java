package com.example.shopv2.service;

import com.example.shopv2.controller.dto.BasketResponse;
import com.example.shopv2.exceptions.BasketException;
import com.example.shopv2.mapper.BasketMapper;
import com.example.shopv2.mapper.NutritionMapper;
import com.example.shopv2.model.*;
import com.example.shopv2.pojo.NutritionNutrientPojo;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import com.example.shopv2.pojo.RecipesPojo;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.NutritionRepository;
import com.example.shopv2.repository.UserRepository;
import com.example.shopv2.service.user.UserLogService;
import com.example.shopv2.validator.BasketValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BasketServiceTest {

    private BasketService basketService;
    private BasketRepository basketRepository;
    private RecipesService recipesService;
    private NutritionService nutritionService;
    private IngredientService ingredientService;
    private IngredientRepository ingredientRepository;
    private NutritionRepository nutritionRepository;
    private BasketValidator basketValidator = new BasketValidator();
    @Mock
    private UserRepository userRepository;
    private NutritionMapper nutritionMapper = new NutritionMapper();
    private BasketMapper basketMapper = new BasketMapper();
    private UserLogService userLogService;
    @BeforeEach
    public void setup(){
        basketRepository = Mockito.mock(BasketRepository.class);
        recipesService = Mockito.mock(RecipesService.class);
        nutritionService = Mockito.mock(NutritionService.class);
        ingredientService = Mockito.mock(IngredientService.class);
        ingredientRepository = Mockito.mock(IngredientRepository.class);
        nutritionRepository = Mockito.mock(NutritionRepository.class);
//        basketValidator = Mockito.mock(BasketValidator.class);
        userLogService = mock(UserLogService.class);
        basketService = new BasketService(basketRepository, recipesService, nutritionService, ingredientService,
                ingredientRepository, nutritionRepository, basketValidator, null, nutritionMapper,
                basketMapper, userLogService, null);
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
        ArrayList<NutritionNutrientPojo> nutritionNutrientPojos = new ArrayList<>();
        when(ingredientService.getIngredientByRecipesId(22)).thenReturn(List.of(new RecipesIngredientPojo()));
        when(nutritionService.getNutritionByIngredientId(22L)).thenReturn(nutritionNutrientPojos);

        //when
        basketService.saveAllByRecipesId(22);

        //then
        ArgumentCaptor<Basket> argumentCaptor1 = ArgumentCaptor.forClass(Basket.class);
        verify(basketRepository, times(1)).save(argumentCaptor1.capture());
    }

    @Test
    void saveNutritionByIngredientId_shouldSaveIngredient(){
        //given
        NutritionNutrientPojo data = NutritionNutrientPojo
                .builder()
                .amount(23)
                .build();;
        ArrayList<NutritionNutrientPojo> expectedList = new ArrayList<>();
        expectedList.add(data);
        Integer id = 22;

        when(nutritionService.getNutritionByIngredientId(Long.valueOf(id))).thenReturn(expectedList);

        //when
        basketService.saveNutritionByIngredientId(id);

        //then
        ArgumentCaptor<Nutrition> argumentCaptor = ArgumentCaptor.forClass(Nutrition.class);
        verify(nutritionRepository).save(argumentCaptor.capture());
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

    @Test
    void getCardByUserId_shouldReturnCardListByUser(){
        //given
        UserEntity user = initUser();
        Basket data = Basket
                .builder()
                .userEntity(user)
                .build();
        ArrayList<Basket> expectedData = new ArrayList<>();
        expectedData.add(data);

        when(basketService.getBasketByUser()).thenReturn(expectedData);
        when(basketRepository.findAllByUserEntity(user)).thenReturn(expectedData);

        //when
        List<Basket> actualData = basketService.getBasketByUser();

        //then
        assertThat(actualData).isEqualTo(expectedData);
    }

    @Test
    void summingNutritionByBasket_shouldSumListOfNutrition() {
        //given
        when(userLogService.loggedUser()).thenReturn(initUser());
        List<Nutrition> nutritionList = initNutritionData();
        Basket basket = Basket
                .builder()
                .nutritionList(nutritionList)
                .userEntity(initUser())
                .build();
        ArrayList<Basket> data  = new ArrayList<>();
        data.add(basket);

        when(basketRepository.findAllByUserEntity(initUser())).thenReturn(data);

        Nutrition nutrition3 = Nutrition
                .builder()
                .name("name")
                .amount(4)
                .build();
        ArrayList<Nutrition> expectedList  = new ArrayList<>();
        expectedList.add(nutrition3);

        //when
        List<Nutrition> actualList = basketService.summingNutritionByBasket();

        //then
        assertThat(expectedList).isEqualTo(actualList);
    }

    @Test
    void sumIngredientByBasketId_shouldSumIngredients(){
        //given
        List<Ingredient> ingredientList = initIngredientData();

        Basket basket1 = Basket
                .builder()
                .ingredients(ingredientList)
                .build();
        ArrayList<Basket> expectedData  = new ArrayList<>();
        expectedData.add(basket1);

        Ingredient ingredient3 = Ingredient
                .builder()
                .name("name")
                .amount(4)
                .build();
        ArrayList<Ingredient> expectedList  = new ArrayList<>();
        expectedList.add(ingredient3);

        UserEntity user = initUser();
        when(basketService.sumIngredientByBasketId()).thenReturn(expectedList);
        when(basketRepository.findAllByUserEntity(user)).thenReturn(expectedData);

        //when
        List<Ingredient> actualList = basketService.sumIngredientByBasketId();

        //then
        assertThat(expectedList).isEqualTo(actualList);
        assertAll(
                () -> assertThat(actualList).hasSize(1),
                ()-> assertThat(actualList.get(0).getName()).isEqualTo("name"),
                ()-> assertThat(actualList.get(0).getAmount()).isEqualTo(4)
        );
    }

    @Test
    void getListOfRecipesByBasketId_shouldReturnListOfRecipes(){
        //given

        Recipes recipes1 = Recipes
                .builder()
                .idRecipesAPI(12)
                .build();

        ArrayList<Recipes> recipesList = new ArrayList<>();
        recipesList.add(recipes1);

        Basket basket = Basket
                .builder()
                .id(5L)
                .idUser(2222L)
                .recipes(recipesList)
                .build();

        ArrayList<Basket> basketList = new ArrayList<>();
        basketList.add(basket);

        when(basketRepository.findAllByIdUser(2222L)).thenReturn(basketList);

        //when
        List<RecipesPojo> actualData = basketService.getListOfRecipesByUserId();

        //then
        assertThat(actualData).hasSize(1);
    }

    @Test
    void filterBasketBetweenDate_shouldReturnFilteredByDate(){
        //given
        var fromDate = "2021-01-04T14:56:04+02:00";
        var toDate = "2021-01-10T14:56:04+02:00";
        Basket basket = Basket
                .builder()
                .dataAdded(OffsetDateTime.parse("2021-01-05T14:56:04+02:00",  DateTimeFormatter.ISO_DATE_TIME))
                .build();
        List<Basket> data = new ArrayList<>();
        data.add(basket);
        when(basketRepository.findAllByBetweenDate(OffsetDateTime.parse(fromDate), OffsetDateTime.parse(toDate))).thenReturn(data);

        //when
        List<BasketResponse> result = basketService.filterBasketBetweenDate(fromDate, toDate);

        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDataAdded()).isEqualTo("2021-01-05T14:56:04+02:00");
    }

    private List<Ingredient> initIngredientData(){
        Ingredient ingredient1 = Ingredient
                .builder()
                .name("name")
                .amount(2)
                .build();

        Ingredient ingredient2 = Ingredient
                .builder()
                .name("name")
                .amount(2)
                .build();

        ArrayList<Ingredient> ingredientList  = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        return ingredientList;
    }

    private List<Nutrition> initNutritionData(){
        Nutrition nutrition1 = Nutrition
                .builder()
                .name("name")
                .amount(2)
                .build();

        Nutrition nutrition2 = Nutrition
                .builder()
                .name("name")
                .amount(2)
                .build();

        ArrayList<Nutrition> nutritionList  = new ArrayList<>();
        nutritionList.add(nutrition1);
        nutritionList.add(nutrition2);
        return nutritionList;
    }

    @Test
    void deleteBasketByUser_deleteAllBasketsByUser(){
        //given
        when(userLogService.loggedUser()).thenReturn(initUser());
        //when
        basketService.deleteBasketByUser();

        //when
        verify(basketRepository).deleteAllByUserEntity(initUser());
    }

    private UserEntity initUser(){
        return UserEntity
                .builder()
                .username("test_name")
                .password("test_pass")
                .build();
    }
}
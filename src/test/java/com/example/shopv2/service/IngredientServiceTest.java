package com.example.shopv2.service;

import com.example.shopv2.exceptions.IngredientException;
import com.example.shopv2.model.Basket;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.validator.IngredientValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class IngredientServiceTest {

    private BasketRepository basketRepository;
    private IngredientRepository ingredientRepository;
    private IngredientService ingredientService;
    private IngredientValidator ingredientValidator = new IngredientValidator();

    @BeforeEach
    public void setup(){
        basketRepository = mock(BasketRepository.class);
        ingredientRepository = mock(IngredientRepository.class);
        ingredientService = new IngredientService(basketRepository, ingredientRepository, ingredientValidator, null, null);
    }

    @Test
    void listOfIdCards_checkIfIdExists_throwException() {
        //given//when//then
        assertThrows(IngredientException.class, () -> ingredientService.listOfIdCards(0L));
        assertThrows(IngredientException.class, () -> ingredientService.listOfIdCards(null));
        assertThrows(IngredientException.class, () -> ingredientService.listOfIdCards(-1L));
    }

    @Test
    void listOfIdCards_shouldReturnListOfLongs() {
        //given
        List<Long> expected = new ArrayList<>();
        ArrayList<Basket> objects = new ArrayList<>();
        when(basketRepository.findAllByIdUser(2222L)).thenReturn(objects);

        // when
        List<Long> actual = ingredientService.listOfIdCards(2222L);

        // then
        assertThat(expected).isEqualTo(actual);
    }
    @Test
    void listOfIngredientsByCardId_ifListOfIdsInEmpty_throwException(){
        //given
        ArrayList<Long> longs = new ArrayList<>();

        //when// then
        assertThrows(IngredientException.class, () -> ingredientService.listOfIngredientsByCardId(longs));
    }

    @Test
    void listOfIngredientsByCardId_shouldReturnListOfIngredients(){
        //given
        ArrayList<Long> ids = new ArrayList<>();
        ids.add(222L);
        ArrayList<Ingredient> expected = new ArrayList<>();
        when(ingredientRepository.findAllByBasketIdIn(ids)).thenReturn(expected);

        //when
        List<Ingredient> actual = ingredientService.listOfIngredientsByCardId(ids);

        //then
        assertEquals(actual,expected);
    }

    @Test
    void sumIngredients_shouldSumTwoIngredients_returnSumOfTwoIngredients(){
        //given
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
        ArrayList<Ingredient> dataList = new ArrayList<>();
        dataList.add(ingredient1);
        dataList.add(ingredient2);

        Ingredient ingredient3 = Ingredient
                .builder()
                .name("name")
                .amount(4)
                .build();
        ArrayList<Ingredient> expectedList = new ArrayList<>();
        expectedList.add(ingredient3);

        List<String> ingredientNames = ingredientService.distinctNames(dataList);

        //when
        List<Ingredient> actualList = ingredientService.sumIngredients(ingredientNames, dataList);

        //then
        assertThat(actualList).hasSize(1);
        assertThat(expectedList.get(0).getName()).isEqualTo("name");
        assertEquals(actualList, expectedList);
    }

    @Test
    public void getIngredientsByCardId_checkWhenIdIsNotCorrect_throwException(){
        //given//when//then
        assertThrows(IngredientException.class, () -> ingredientService.getIngredientsByCardId(0));
        assertThrows(IngredientException.class, () -> ingredientService.getIngredientsByCardId(-1));
    }

    @Test
    public void getIngredientsByCardId_shouldReturnOneIngredientObject(){
        //given
        final Long ID =22L;
        Ingredient ingredient1 = Ingredient
                .builder()
                .name("name")
                .amount(2)
                .build();

        ArrayList<Ingredient> expectedList = new ArrayList<>();
        expectedList.add(ingredient1);
        when(ingredientRepository.findAllByBasketId(ID)).thenReturn(expectedList);

        //when
//        List<Ingredient> actualList = ingredientService.getIngredientsByCardId(Math.toIntExact(ID));

        //then
//        assertThat(actualList).hasSize(1).isEqualTo(expectedList);

    }
}
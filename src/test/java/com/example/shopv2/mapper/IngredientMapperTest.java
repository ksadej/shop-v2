package com.example.shopv2.mapper;

import com.example.shopv2.model.Basket;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class IngredientMapperTest {

    @Mock
    private IngredientMapper ingredientMapper = new IngredientMapper();

    private RecipesIngredientPojo givenRecipesIngredientPojo(){
        return RecipesIngredientPojo
                .builder()
                .name("name")
                .image("img")
                .consistency("cons")
                .original("org")
                .aisle("ais")
                .build();
    }

    @Test
    public void shouldMapRequestToIngredientObject(){
        //given
        RecipesIngredientPojo recipesIngredientPojo = givenRecipesIngredientPojo();

        //when
        Ingredient ingredient = ingredientMapper.requestToEntity(recipesIngredientPojo);

        //then
        Assertions.assertEquals("name", ingredient.getName());
        Assertions.assertEquals("img", ingredient.getImage());
        Assertions.assertEquals("cons", ingredient.getConsistency());
        Assertions.assertEquals("org", ingredient.getOriginal());
        Assertions.assertEquals("ais", ingredient.getAisle());
    }
    @Test
    public void checkTimesAndReturnedType(){
        //given
        ingredientMapper = Mockito.mock(IngredientMapper.class);
        Mockito.when(ingredientMapper.requestToEntity(new RecipesIngredientPojo())).thenReturn(new Ingredient());

        //when
        ingredientMapper.requestToEntity(new RecipesIngredientPojo());

        //then
        ArgumentCaptor<RecipesIngredientPojo> argumentCaptor = ArgumentCaptor.forClass(RecipesIngredientPojo.class);
        verify(ingredientMapper, times(1)).requestToEntity(argumentCaptor.capture());
    }

}
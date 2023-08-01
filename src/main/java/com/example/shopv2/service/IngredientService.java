package com.example.shopv2.service;

import com.example.shopv2.exceptions.IngredientException;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import com.example.shopv2.validator.IngredientValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService{

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientService.class.getName());

    private final BasketRepository basketRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientValidator ingredientValidator;

    @Autowired
    public IngredientService(BasketRepository basketRepository,
                             IngredientRepository ingredientRepository,
                             IngredientValidator ingredientValidator) {
        this.basketRepository = basketRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientValidator = ingredientValidator;
    }

    //pobiera składniki z przepisu na podstawie id przepisu
    public List<RecipesIngredientPojo> getIngredientByRecipesId(Integer id){
        LOGGER.info("Getting ingredients by recipes id");
        LOGGER.debug("Recipes id: "+id);
        ArrayList<RecipesIngredientPojo> extendedIngredients =
                Connection.externalApiConnectionGET(
                        "https://api.spoonacular.com/recipes/"+id+"/information?includeNutrition=false", RecipesIngredientPojo.class).getExtendedIngredients();
        LOGGER.debug("List of ingredients"+ extendedIngredients);

        return extendedIngredients;
    }

    //pobiera listę wszystkich składkików
    public List<Ingredient> getIngredientsByCardId(Integer id){
        ingredientValidator.valid(Long.valueOf(id));
        return ingredientRepository.findAllByBasketId(Long.valueOf(id));
    }

    //lista id produktów pobrana na podstawie id user
    List<Long> listOfIdCards(Long id){
        ingredientValidator.valid(id);

        return basketRepository.findByIdUser(id)
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
    }

    //lista składników pobranych na podstawie card id
    List<Ingredient> listOfIngredientsByCardId(ArrayList<Long> list){
        ingredientValidator.valid2(list);

        return ingredientRepository.findAllByBasketIdIn(list);
    }

    //lista nazw produktów z usuniętymi duplikatami
    List<String> distinctNames(List<Ingredient> ingredients){
        return ingredients.stream()
                .map(Ingredient::getName)
                .distinct()
                .toList();
    }

    List<Ingredient> sumIngredients(List<String> ingredientNames, List<Ingredient> ingredients){
        List<Ingredient> newIngredients = new ArrayList<>();
        for(String st : ingredientNames) {
            Ingredient ingredient = Ingredient
                    .builder()
                    .name(st)
                    .amount(ingredients.stream()
                            .filter(name -> name.getName().equals(st))
                            .mapToDouble(amout -> amout.getAmount())
                            .sum())
                    .build();
            newIngredients.add(ingredient);
        }
        return newIngredients;
    }


    //funkcja do podsumowania ilości produktów które mamy zakupić
    public List<Ingredient> sumAllIngredientsByUserId(Long id){

        // lista card id
        LOGGER.info("Sum ingredients by USER id");
        LOGGER.debug("USER id: "+id);
        //lista id produktów pobrana na podstawie id user
        List<Long> longList = this.listOfIdCards(id);

        //lista składników pobranych na podstawie card id
        List<Ingredient> ingredients = this.listOfIngredientsByCardId((ArrayList<Long>) longList);

        //lista nazw produktów z usuniętymi duplikatami
        List<String> ingredientNames = this.distinctNames(ingredients);

        //lista do której zapisuję nowe zbudowane obiekty
        return sumIngredients(ingredientNames, ingredients);
    }

    //filtry: po cenie produktu od najmniejszego do największego
    //filtry: po kategorii: owoce, warzywa itd.
}

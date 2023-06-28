package com.example.shopv2.service;

import com.example.shopv2.mapper.IngredientMapper;
import com.example.shopv2.mapper.NutritionMapper;
import com.example.shopv2.model.Basket;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.model.Nutrition;
import com.example.shopv2.model.Recipes;
import com.example.shopv2.pojo.RecipesPojo;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.NutritionRepository;
import com.example.shopv2.pojo.NutritionNutrientPojo;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketService.class.getName());
    private final BasketRepository basketRepository;
    private final RecipesService recipesService;
    private final NutritionService nutritionService;
    private final IngredientService ingredientService;
    private final IngredientRepository ingredientRepository;
    private final NutritionRepository nutritionRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository,
                       RecipesService recipesService,
                       NutritionService nutritionService,
                       IngredientService ingredientService,
                       IngredientRepository ingredientRepository,
                       NutritionRepository nutritionRepository) {
        this.basketRepository = basketRepository;
        this.recipesService = recipesService;
        this.nutritionService = nutritionService;
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
        this.nutritionRepository = nutritionRepository;
    }


    @Transactional
    public void saveRecipesAndIngredientsByRecipesId(Integer id){
        LOGGER.info("Saving ingredients in basket by recipes ID");

        Basket basket = new Basket();

        ///Recipes
        Recipes ing = Recipes
                .builder()
                .idRecipesAPI(id)
                .basket(basket)
                .build();
        ///End of Recipes

        ///Ingredients
        List<RecipesIngredientPojo> recipesIngredientPojo = ingredientService.getIngredientByRecipesId(id);
        IngredientMapper ingredientMapper = new IngredientMapper();
        List<Ingredient> ingredients = recipesIngredientPojo
                .stream()
                .map(IngredientMapper -> ingredientMapper.requestToEntity(IngredientMapper))
                .peek(x -> x.setBasket(basket))
                .collect(Collectors.toList());
        ///End of Ingredients

        basket.setRecipes(List.of(ing));
        basket.setIngredients(ingredients);
        basketRepository.save(basket);
    }

    // zapisuje Listę wartości odzywczych do tabeli Nutrition na podstawie id przepisu
    public void saveNutritionByIngredientId(Integer id){
        //zapisuje do Listy, listę składników przepisu pobranego na podstawie id recepty
        List<RecipesIngredientPojo> recipesIngredientPojos = ingredientService.getIngredientByRecipesId(id);
        List<Integer> idsOfIngredients = recipesIngredientPojos
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());

        NutritionMapper nutritionMapper = new NutritionMapper();
        ArrayList<NutritionNutrientPojo> nu = null;
        for(int i=0; i<idsOfIngredients.size(); i++){
            nu = nutritionService.getNutritionByIngredientId(Long.valueOf(idsOfIngredients.get(i)));
        }

        Ingredient ingredient = new Ingredient();
        List<Nutrition> nutritions = nu.stream()
                .map(NutritionMapper -> nutritionMapper.requestToEntity(NutritionMapper))
                .peek(x ->x.setIngredient(ingredient))
                .collect(Collectors.toList());

        ingredient.setNutrition(nutritions);
        ingredientRepository.save(ingredient);
    }

    // zapisuje listę składników wraz z wartościami z przepisu na podstawie id przepisu
    public void saveAllByRecipesId(Integer id){

        //zapisuje do Listy, listę składników przepisu pobranego na podstawie id recepty
        List<RecipesIngredientPojo> recipesIngredientPojo = ingredientService.getIngredientByRecipesId(id);

        List<Integer> idsOfIngredients = recipesIngredientPojo.stream().map(x -> x.getId()).collect(Collectors.toList());
        LOGGER.debug("List of ingredients id: "+ idsOfIngredients);

        IngredientMapper ingredientMapper = new IngredientMapper();
        NutritionMapper nutritionMapper = new NutritionMapper();
        ArrayList<NutritionNutrientPojo> nu;
        Basket basket = new Basket();
        for(int i=0; i< recipesIngredientPojo.size(); i++){
            Ingredient cc = ingredientMapper.ingredientPojoToIngredient(recipesIngredientPojo, i);
            cc.setBasket(basket);

            System.out.println("Wyszukano wartośści odzywcze nr "+i);
            nu = nutritionService.getNutritionByIngredientId(Long.valueOf(idsOfIngredients.get(i)));
            Ingredient ingredientObject = new Ingredient();

            for(int j=0; j<nu.size(); j++){
                Nutrition nutrition = nutritionMapper.nutritionPojoToNutrition(nu, j);
                nutrition.setIngredient(ingredientObject);

                ingredientObject.getNutrition().add(nutrition);
                ingredientRepository.save(ingredientObject);
            }

            basket.getIngredients().add(cc);
            basketRepository.save(basket);
        }

        //pobieram obiekt RecipesPojo i zapisuje go do nowego obiektu
        RecipesPojo recipesPojos = recipesService.getRecipesById(id);
        Recipes recipes = Recipes
                .builder()
                .idRecipesAPI(recipesPojos.id)
                .basket(basket)
                .build();

        basket.getRecipes().add(recipes);
        basketRepository.save(basket);
    }

    //pobiera kartę produktów na podstawie id użytkownika
    public List<Basket> getCardByUserId(Long id){
        return basketRepository.findAllByIdUser(id);
    }
}

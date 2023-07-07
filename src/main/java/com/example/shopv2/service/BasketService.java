package com.example.shopv2.service;

import com.example.shopv2.mapper.IngredientMapper;
import com.example.shopv2.mapper.NutritionMapper;
import com.example.shopv2.model.Basket;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.model.Nutrition;
import com.example.shopv2.model.Recipes;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.NutritionRepository;
import com.example.shopv2.pojo.NutritionNutrientPojo;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import com.example.shopv2.validator.BasketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
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
    private final BasketValidator basketValidator;
    private final NutritionMapper nutritionMapper;


    @Autowired
    public BasketService(BasketRepository basketRepository,
                         RecipesService recipesService,
                         NutritionService nutritionService,
                         IngredientService ingredientService,
                         IngredientRepository ingredientRepository,
                         NutritionRepository nutritionRepository, BasketValidator basketValidator, NutritionMapper nutritionMapper) {
        this.basketRepository = basketRepository;
        this.recipesService = recipesService;
        this.nutritionService = nutritionService;
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
        this.nutritionRepository = nutritionRepository;
        this.basketValidator = basketValidator;
        this.nutritionMapper = nutritionMapper;
    }

    @Transactional
    public void saveRecipesAndIngredientsByRecipesId(Integer id) {
        LOGGER.info("Saving ingredients in basket by recipes ID");
        basketValidator.basketDataValidator(id);

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
    public void saveNutritionByIngredientId(Integer id) {
        LOGGER.info("Saving nutrition by ingredient ID");
        basketValidator.basketDataValidator(id);

        ArrayList<NutritionNutrientPojo> nutritionList = nutritionService.getNutritionByIngredientId(Long.valueOf(id));
        NutritionMapper nutritionMapper = new NutritionMapper();
        nutritionList
                .stream()
                .map(NutritionMapper -> nutritionMapper.requestToEntity(NutritionMapper))
                .peek(x ->x.setIdIngredientAPI(id))
                .forEach(NutritionRepository -> nutritionRepository.save(NutritionRepository));
    }

    // zapisuje listę składników wraz z wartościami z przepisu na podstawie id przepisu
    @Transactional
    public void saveAllByRecipesId(Integer id) {
        LOGGER.info("Saving ingredients in basket by recipes ID");
        basketValidator.basketDataValidator(id);

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

        ///Nutrition
        List<Integer> ids = ingredientService.getIngredientByRecipesId(id)
                .stream()
                .map(x ->x.getId())
                .toList();
        Iterator<Integer> iterator = ids.iterator();

//        ArrayList<NutritionNutrientPojo> nutritionByIngredientId =null;
//        for(int i=0; i<ids.size(); i++){
//            nutritionByIngredientId = nutritionService.getNutritionByIngredientId(Long.valueOf(ids.get(i)));
//        }

        ArrayList<NutritionNutrientPojo> nutritionByIngredientId =
                 nutritionService.getNutritionByIngredientId(Long.valueOf(iterator.next()));

        List<Nutrition> nutritions = nutritionByIngredientId
                .stream()
                .map(NutritionMapper -> nutritionMapper.requestToEntity(NutritionMapper))
                .peek(x -> x.setBasket(basket))
                .collect(Collectors.toList());

        basket.setRecipes(List.of(ing));
        basket.setIngredients(ingredients);
        basket.setNutritionList(nutritions);
        basketRepository.save(basket);
    }

    //pobiera kartę produktów na podstawie id użytkownika
    public List<Basket> getCardByUserId(Long id){
        LOGGER.info("Getting card by user ID");
        basketValidator.basketDataValidator(Math.toIntExact(id));

        return basketRepository.findAllByIdUser(id);
    }

    public List<Nutrition> summingNutritionByBasket(){
        List<Basket> baskets = basketRepository.findAllByIdUser(2222L);

        List<Nutrition> nutritions = baskets.get(0).getNutritionList();
        System.out.println(nutritions);
        Set<String> nutritionNames = nutritions
                .stream()
                .map(Nutrition::getName)
                .collect(Collectors.toSet());


    Nutrition nn = Nutrition
            .builder()
            .name("Vitamin B12")
            .amount(nutritions.stream()
                    .filter(c -> c.getName().equals("Vitamin B12"))
                    .map(x->x.getAmount())
                    .mapToDouble(Double::doubleValue)
                    .sum())
            .build();

//        System.out.println(nutrition1);

        return List.of(nn);
    }

    //metoda do sumowania ceny i naliczania rabatów
    //metoda do sumowania nutrions data
    //metoda do sumowania wszystkich składników
    //metoda do pobierania proponowanych przepisów na podstawie produktów
}

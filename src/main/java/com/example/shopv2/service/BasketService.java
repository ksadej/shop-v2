package com.example.shopv2.service;

import com.example.shopv2.controller.dto.BasketResponse;
import com.example.shopv2.filters.BasketFilterRange;
import com.example.shopv2.filters.FilterRangeAbstract;
import com.example.shopv2.mapper.BasketMapper;
import com.example.shopv2.mapper.IngredientMapper;
import com.example.shopv2.mapper.NutritionMapper;
import com.example.shopv2.model.*;
import com.example.shopv2.pojo.RecipesPojo;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.NutritionRepository;
import com.example.shopv2.pojo.NutritionNutrientPojo;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import com.example.shopv2.service.user.UserLogService;
import com.example.shopv2.validator.BasketValidator;
import com.example.shopv2.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private final UserValidator userValidator;
    private final NutritionMapper nutritionMapper;
    private final BasketMapper basketMapper;
    private final UserLogService userLogService;

    private final FilterRangeAbstract<Basket> filterRangeAbstract;

    @Autowired
    public BasketService(BasketRepository basketRepository,
                         RecipesService recipesService,
                         NutritionService nutritionService,
                         IngredientService ingredientService,
                         IngredientRepository ingredientRepository,
                         NutritionRepository nutritionRepository,
                         BasketValidator basketValidator,
                         UserValidator userValidator, NutritionMapper nutritionMapper,
                         BasketMapper basketMapper,
                         UserLogService userLogService, BasketFilterRange basketFilterRange) {
        this.basketRepository = basketRepository;
        this.recipesService = recipesService;
        this.nutritionService = nutritionService;
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
        this.nutritionRepository = nutritionRepository;
        this.basketValidator = basketValidator;
        this.userValidator = userValidator;
        this.nutritionMapper = nutritionMapper;
        this.basketMapper = basketMapper;
        this.userLogService = userLogService;
        this.filterRangeAbstract = basketFilterRange;
    }

    @Transactional
    public void saveRecipesAndIngredientsByRecipesId(Integer id) {
        LOGGER.info("Saving ingredients in basket by recipes ID");
        basketValidator.basketDataValidator(id);

        Basket basket = new Basket();
        basket.setDataAdded(OffsetDateTime.now());

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

    // zapisuje Listę wartości odzywczych do tabeli Nutrition na podstawie id składników
    @Transactional
    public void saveNutritionByIngredientId(Integer id) {
        LOGGER.info("Saving nutrition by ingredient ID");
        basketValidator.basketDataValidator(id);

        ArrayList<NutritionNutrientPojo> nutritionList = nutritionService.getNutritionByIngredientId(Long.valueOf(id));
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
        UserEntity user = userLogService.loggedUser();

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
        basket.setUserEntity(user);
        basket.setDataAdded(OffsetDateTime.now());
        basketRepository.save(basket);
    }

    //pobiera kartę produktów na podstawie id użytkownika
    public List<Basket> getCardByUserId(){
        LOGGER.info("Getting card by user ID");
        UserEntity user = userLogService.loggedUser();

        return basketRepository.findAllByUserEntity(user);
    }

    //sumuje wartosci nutrition na podstawie basket id
    public List<Nutrition> summingNutritionByBasket(){
        UserEntity user = userLogService.loggedUser();
        LOGGER.info("Summing nutrution by basket "+ user.getUsername());
        List<Basket> baskets = basketRepository.findAllByUserEntity(user);

        List<Nutrition> nutritions = baskets.get(0).getNutritionList();
        Set<String> nutritionNames = nutritions
                .stream()
                .map(Nutrition::getName)
                .collect(Collectors.toSet());

        Map<String, Double> sumNut = nutritions
                .stream()
                .collect(Collectors.groupingBy(Nutrition::getName,
                        Collectors.summingDouble(Nutrition::getAmount)));

        List<String> strings = nutritionNames.stream().collect(Collectors.toList());
        ArrayList<Nutrition> nutritionArrayList = new ArrayList<>();
        for(int i=0; i< sumNut.size(); i++) {
            String name = strings.get(i);

            Nutrition nutrition = Nutrition
                    .builder()
                    .name(name)
                    .amount(sumNut.get(name))
                    .build();
            nutritionArrayList.add(nutrition);
        }
        return nutritionArrayList;
    }

    //sumuje składniki na podstawie koszyka właściciela
    public List<Ingredient> sumIngredientByBasketId(){
        UserEntity user = userLogService.loggedUser();
        List<Basket> baskets = basketRepository.findAllByUserEntity(user);

        List<Ingredient> ingredients = baskets.get(0).getIngredients();

        Set<String> strings = ingredients
                .stream()
                .map(x->x.getName())
                .collect(Collectors.toSet());

        Map<String, Double> sumIng = ingredients
                .stream()
                .collect(Collectors.groupingBy(Ingredient::getName,
                        Collectors.summingDouble(Ingredient::getAmount)));

        List<String> ingredientName = strings.stream().toList();
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        for(int i=0; i< ingredientName.size(); i++){
            String name = ingredientName.get(i);
            Ingredient ingredient = Ingredient
                    .builder()
                    .name(name)
                    .amount(sumIng.get(name))
                    .build();
            ingredientArrayList.add(ingredient);
        }
        return ingredientArrayList;
    }

    //pobiera liste recept z koszyka na podstawie użytkownika
    @Transactional
    public List<RecipesPojo> getListOfRecipesByUserId(){
        UserEntity user = userLogService.loggedUser();
        List<Basket> baskets = basketRepository.findAllByUserEntity(user);

        List<Integer> recipesIds = baskets.get(0).getRecipes()
                .stream()
                .map(x -> x.getIdRecipesAPI())
                .collect(Collectors.toList());

        List<RecipesPojo> recipesPojoList = new ArrayList<>();
        for(int i=0; i<recipesIds.size(); i++){
            recipesPojoList.add(recipesService.getRecipesById(recipesIds.get(i)));
        }

        return recipesPojoList;
    }

    public Double sumPriceByBasketId(Integer id){
       Double recipesPojoList = this.getListOfRecipesByUserId()
               .stream()
               .map(x->x.getPricePerServing())
               .mapToDouble(Double::doubleValue)
               .sum();

        return recipesPojoList;
    }

    public List<BasketResponse> filterBasketBetweenDate(String fromDate, String toDate){

        OffsetDateTime fData = OffsetDateTime.parse(fromDate,  DateTimeFormatter.ISO_DATE_TIME);
        OffsetDateTime tDate = OffsetDateTime.parse(toDate,  DateTimeFormatter.ISO_DATE_TIME);

        List<BasketResponse> basketResponses = basketRepository.findAllByBetweenDate(fData, tDate)
                .stream()
                .map(BasketMapper -> basketMapper.entityToResponse(BasketMapper))
                .collect(Collectors.toList());
        System.out.println(basketResponses);
        return basketResponses;
    }

    public List<BasketResponse> getAllFilteredBasket(Map<String, String> filter){

        return filterRangeAbstract.getAllByFilters(filter)
                .stream()
                .map(BasketMapper -> basketMapper.entityToResponse(BasketMapper))
                .collect(Collectors.toList());
    }


    //metoda naliczania rabatów na całość zakupów i na konkretne przepisy
    //metoda do pobierania proponowanych przepisów na podstawie produktów
}

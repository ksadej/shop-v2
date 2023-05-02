package com.example.shopv2.service;

import com.example.shopv2.mapper.IngredientMapper;
import com.example.shopv2.mapper.NutritionMapper;
import com.example.shopv2.model.Card;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.model.Nutrition;
import com.example.shopv2.repository.CardRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.NutritionRepository;
import com.example.shopv2.pojo.NutritionNutrientPojo;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardService.class.getName());
    private final CardRepository cardRepository;
    private final RecipesService recipesService;
    private final NutritionService nutritionService;
    private final IngredientService ingredientService;
    private final IngredientRepository ingredientRepository;
    private final NutritionRepository nutritionRepository;
    @Autowired
    public CardService(CardRepository cardRepository,
                       RecipesService recipesService,
                       NutritionService nutritionService,
                       IngredientService ingredientService,
                       IngredientRepository ingredientRepository,
                       NutritionRepository nutritionRepository) {
        this.cardRepository = cardRepository;
        this.recipesService = recipesService;
        this.nutritionService = nutritionService;
        this.ingredientService = ingredientService;
        this.ingredientRepository = ingredientRepository;
        this.nutritionRepository = nutritionRepository;
    }


    // zapisuje składniki z przepisu na podstawie id przepisu
    public void saveRecipesIngredientsByRecipesId(Integer id){
        LOGGER.info("Saving ingredients by recipes ID");
        //zapisuje do Listy, listę składników przepisu pobranego na podstawie id recepty
        List<RecipesIngredientPojo> recipesIngredientPojo = ingredientService.getIngredientByRecipesId(id);

        IngredientMapper ingredientMapper = new IngredientMapper();
        Card card = new Card();
        for(int i=0; i< recipesIngredientPojo.size(); i++){
            Ingredient cc = ingredientMapper.ingredientPojoToIngredient(recipesIngredientPojo, i);
            cc.setCard(card);
            card.getIngredients().add(cc);
            cardRepository.save(card);
        }
    }


    // zapisuje listę składników wraz z wartościami z przepisu na podstawie id przepisu
    public void saveIngredientsAndNutritionByRecipesId(Integer id){

        //zapisuje do Listy, listę składników przepisu pobranego na podstawie id recepty
        List<RecipesIngredientPojo> recipesIngredientPojo = ingredientService.getIngredientByRecipesId(id);

        List<Integer> idsOfIngredients = recipesIngredientPojo.stream().map(x -> x.getId()).collect(Collectors.toList());
        LOGGER.debug("List of ingredients id: "+ idsOfIngredients);

        IngredientMapper ingredientMapper = new IngredientMapper();
        ArrayList<NutritionNutrientPojo> nu;
        Card card = new Card();
        for(int i=0; i< recipesIngredientPojo.size(); i++){
            Ingredient cc = ingredientMapper.ingredientPojoToIngredient(recipesIngredientPojo, i);
            cc.setCard(card);

            System.out.println("Wyszukano wartośści odzywcze nr "+i);
            nu = nutritionService.getNutritionByIngredientId(Long.valueOf(idsOfIngredients.get(i)));
            Ingredient ingredientObject = new Ingredient();

            for(int j=0; j<nu.size(); j++){
                Nutrition nutrition = Nutrition
                        .builder()
                        .name(nu.stream().map(x -> x.getName()).collect(Collectors.toList()).get(j))
                        .amount(nu.stream().map(x -> x.getAmount()).collect(Collectors.toList()).get(j))
                        .percentOfDailyNeeds(nu.stream().map(x -> x.getAmount()).collect(Collectors.toList()).get(j))
                        .unit(nu.stream().map(x -> x.getUnit()).collect(Collectors.toList()).get(j))
                        .ingredient(ingredientObject)
                        .build();

                ingredientObject.getNutrition().add(nutrition);
                ingredientRepository.save(ingredientObject);
            }

            card.getIngredients().add(cc);
            cardRepository.save(card);
        }
    }

    // zapisuje Listę wartości odzywczych do tabeli Nutrition na podstawie id przepisu
    public void saveNutritionByIngredientIdTEST(Integer id){

        //zapisuje do Listy, listę składników przepisu pobranego na podstawie id recepty
        List<RecipesIngredientPojo> recipesIngredientPojos = ingredientService.getIngredientByRecipesId(id);
        List<Integer> idsOfIngredients = recipesIngredientPojos.stream().map(x -> x.getId()).collect(Collectors.toList());
        System.out.println("Lista IDS składników: "+idsOfIngredients);

        NutritionMapper nutritionMapper = new NutritionMapper();
        Ingredient ingredientObject = new Ingredient();
        ArrayList<NutritionNutrientPojo> nu;
        for(int i=0; i<idsOfIngredients.size(); i++){
            System.out.println("Wyszukano wartośści odzywcze nr "+i);
            nu = nutritionService.getNutritionByIngredientId(Long.valueOf(idsOfIngredients.get(i)));

            for(int j=0; j<nu.size(); j++){
                Nutrition nutrition = nutritionMapper.nutritionPojoToNutrition(nu, j);
                nutritionRepository.save(nutrition);
            }
        }
    }


    //pobiera kartę produktów na podstawie id użytkownika
    public List<Ingredient> getCardByUserId(Long id){
        Card cardList =  cardRepository.findByIdUser(id);
        System.out.println("Id Usera przy pobieraniu karty produktów: "+cardList.getId());

        return ingredientRepository.findByCardId(cardList.getId());
    }
}

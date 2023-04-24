package com.example.shopv2.service;

import com.example.shopv2.model.Card;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.model.Nutrition;
import com.example.shopv2.repository.CardRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.repository.NutritionRepository;
import com.example.shopv2.service.dto.NutritionNutrientResponse;
import com.example.shopv2.service.dto.RecipesIngredientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {

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

        //zapisuje do Listy, listę składników przepisu pobranego na podstawie id recepty
        List<RecipesIngredientResponse> recipesIngredientResponses = ingredientService.getIngredientByRecipesId(id);
        System.out.println("dane jakie dostałem z  przepisów: "+ recipesIngredientResponses);

        //mapuję na lstę Stringów powyższą listę
        List<String> names = recipesIngredientResponses.stream().map(c -> c.getName()).collect(Collectors.toList());
        System.out.println("Lista nazw produktów: "+names);

        Card card = new Card();
        for(int i=0; i< names.size(); i++){
            Ingredient cc = Ingredient
                    .builder()
                    .aisle(recipesIngredientResponses.stream().map(c -> c.getAisle()).collect(Collectors.toList()).get(i))
                    .amount(recipesIngredientResponses.stream().map(c -> c.getAmount()).collect(Collectors.toList()).get(i))
                    .name(recipesIngredientResponses.stream().map(c -> c.getName()).collect(Collectors.toList()).get(i))
                    .consistency(recipesIngredientResponses.stream().map(c -> c.getConsistency()).collect(Collectors.toList()).get(i))
                    .image(recipesIngredientResponses.stream().map(c -> c.getImage()).collect(Collectors.toList()).get(i))
                    .unit(recipesIngredientResponses.stream().map(c -> c.getUnit()).collect(Collectors.toList()).get(i))
                    .original(recipesIngredientResponses.stream().map(c -> c.getOriginal()).collect(Collectors.toList()).get(i))
                    .idIngredientAPI(recipesIngredientResponses.stream().map(c -> c.getId()).collect(Collectors.toList()).get(i))
                    .card(card)
                    .build();

            card.getIngredients().add(cc);

            cardRepository.save(card);
        }
    }

    // TEST
    // zapisuje składniki z przepisu na podstawie id przepisu

    public void saveRecipesIngredientsByRecipesIdTEST(Integer id){

        //zapisuje do Listy, listę składników przepisu pobranego na podstawie id recepty
        List<RecipesIngredientResponse> recipesIngredientResponses = ingredientService.getIngredientByRecipesId(id);

        List<Integer> idsOfIngredients = recipesIngredientResponses.stream().map(x -> x.getId()).collect(Collectors.toList());
        System.out.println("Lista IDS składników: "+idsOfIngredients);

        ArrayList<NutritionNutrientResponse> nu;

        Card card = new Card();
        for(int i=0; i< recipesIngredientResponses.size(); i++){
            Ingredient cc = Ingredient
                    .builder()
                    .aisle(recipesIngredientResponses.stream().map(c -> c.getAisle()).collect(Collectors.toList()).get(i))
                    .amount(recipesIngredientResponses.stream().map(c -> c.getAmount()).collect(Collectors.toList()).get(i))
                    .name(recipesIngredientResponses.stream().map(c -> c.getName()).collect(Collectors.toList()).get(i))
                    .consistency(recipesIngredientResponses.stream().map(c -> c.getConsistency()).collect(Collectors.toList()).get(i))
                    .image(recipesIngredientResponses.stream().map(c -> c.getImage()).collect(Collectors.toList()).get(i))
                    .unit(recipesIngredientResponses.stream().map(c -> c.getUnit()).collect(Collectors.toList()).get(i))
                    .original(recipesIngredientResponses.stream().map(c -> c.getOriginal()).collect(Collectors.toList()).get(i))
                    .idIngredientAPI(recipesIngredientResponses.stream().map(c -> c.getId()).collect(Collectors.toList()).get(i))
                    .card(card)
                    .build();

            card.getIngredients().add(cc);
            System.out.println("zapisany element nr: "+i);
            cardRepository.save(card);
        }

    }

    // zapisuje wartości odzywcze na podstawie id składniku
    public void saveNutritionByIngredientIdTEST(Integer id){

        //zapisuje do Listy, listę składników przepisu pobranego na podstawie id recepty
        List<RecipesIngredientResponse> recipesIngredientResponses = ingredientService.getIngredientByRecipesId(id);
        List<Integer> idsOfIngredients = recipesIngredientResponses.stream().map(x -> x.getId()).collect(Collectors.toList());
        System.out.println("Lista IDS składników: "+idsOfIngredients);

        Ingredient ingredientObject = new Ingredient();
        ArrayList<NutritionNutrientResponse> nu;
        for(int i=0; i<idsOfIngredients.size(); i++){
            System.out.println("Wyszukano wartośści odzywcze nr "+i);
            nu = nutritionService.getNutritionByIngredientId(Long.valueOf(idsOfIngredients.get(i)));

            for(int j=0; j<nu.size(); j++){
                Nutrition nutrition = Nutrition
                        .builder()
                        .name(nu.stream().map(x -> x.getName()).collect(Collectors.toList()).get(j))
                        .amount(nu.stream().map(x -> x.getAmount()).collect(Collectors.toList()).get(j))
                        .percentOfDailyNeeds(nu.stream().map(x -> x.getAmount()).collect(Collectors.toList()).get(j))
                        .unit(nu.stream().map(x -> x.getUnit()).collect(Collectors.toList()).get(j))
                        .ingredient(ingredientObject)
                        .build();

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

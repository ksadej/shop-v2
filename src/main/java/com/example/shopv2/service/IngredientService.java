package com.example.shopv2.service;

import com.example.shopv2.mapper.IngredientMapper;
import com.example.shopv2.model.Ingredient;
import com.example.shopv2.model.UserEntity;
import com.example.shopv2.repository.BasketRepository;
import com.example.shopv2.repository.IngredientRepository;
import com.example.shopv2.pojo.RecipesIngredientPojo;
import com.example.shopv2.service.dto.IngredientDTO;
import com.example.shopv2.service.user.UserLogService;
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
    private final UserLogService userLogService;
    private final IngredientMapper ingredientMapper;

    @Autowired
    public IngredientService(BasketRepository basketRepository,
                             IngredientRepository ingredientRepository,
                             IngredientValidator ingredientValidator, UserLogService userLogService, IngredientMapper ingredientMapper) {
        this.basketRepository = basketRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientValidator = ingredientValidator;
        this.userLogService = userLogService;
        this.ingredientMapper = ingredientMapper;
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
    public List<IngredientDTO> getIngredientsByCardId(Integer id){
        ingredientValidator.valid(Long.valueOf(id));
        ArrayList<Ingredient> allByBasketId = ingredientRepository.findAllByBasketId(Long.valueOf(id));
        List<IngredientDTO> ingredientDTOS = allByBasketId
                .stream()
                .map(x -> ingredientMapper.entityToDto(x))
                .collect(Collectors.toList());
        return ingredientDTOS;
    }

    //lista id produktów pobrana na podstawie id user
    List<Long> listOfIdCards(Long id){
        ingredientValidator.valid(id);
        UserEntity user = userLogService.loggedUser();
        return basketRepository.findAllByUserEntity(user)
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
    public List<IngredientDTO> sumAllIngredientsByUserId(){

        UserEntity user = userLogService.loggedUser();
        // lista card id
        LOGGER.info("Sum ingredients by USER id");
        LOGGER.debug("USER id: "+user.getUsername());
        //lista id produktów pobrana na podstawie id user
        List<Long> longList = this.listOfIdCards(user.getId());

        //lista składników pobranych na podstawie card id
        List<Ingredient> ingredients = this.listOfIngredientsByCardId((ArrayList<Long>) longList);

        //lista nazw produktów z usuniętymi duplikatami
        List<String> ingredientNames = this.distinctNames(ingredients);

        //lista do której zapisuję nowe zbudowane obiekty
        List<Ingredient> newIngredients = sumIngredients(ingredientNames, ingredients);
        List<IngredientDTO> ingredientDtos = newIngredients
                .stream()
                .map(ingredientMapper::entityToDto)
                .toList();
        return ingredientDtos;
    }

    //filtry: po cenie produktu od najmniejszego do największego
    //filtry: po kategorii: owoce, warzywa itd.
}

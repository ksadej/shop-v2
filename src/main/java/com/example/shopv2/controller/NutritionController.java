package com.example.shopv2.controller;

import com.example.shopv2.pojo.NutritionNutrientPojo;
import com.example.shopv2.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class NutritionController {

    private final NutritionService nutritionService;

    @Autowired
    public NutritionController(NutritionService nutritionService) {
        this.nutritionService = nutritionService;
    }

    //pobiera listę wartości odzywczych składnika na podstawie id składnika
    @GetMapping("/nutrition/{id}")
    public ArrayList<NutritionNutrientPojo> getNutritionByIngredientId(@PathVariable Long id){
        return nutritionService.getNutritionByIngredientId(id);
    }

    @PostMapping("/nutrition/save/{id}")
    public void saveNutritionByIngredientId(@PathVariable Long id){
        nutritionService.saveNutritionByIngredientId(id);
    }
}

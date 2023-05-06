package com.example.shopv2.repository;

import com.example.shopv2.model.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Long> {

    public ArrayList<Nutrition> findAllByIngredient(Integer id);
}

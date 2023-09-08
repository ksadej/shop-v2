package com.example.shopv2.repository;

import com.example.shopv2.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    public ArrayList<Ingredient> findAllByBasketId(Long id);

    public ArrayList<Ingredient> findAllByBasketIdIn(ArrayList<Long> id);
}

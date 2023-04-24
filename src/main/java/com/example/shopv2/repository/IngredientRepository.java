package com.example.shopv2.repository;

import com.example.shopv2.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    public List<Ingredient> findByCardId(Long id);
}

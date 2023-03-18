package com.example.shopv2.repository;

import com.example.shopv2.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

//    @Query("SELECT sum(c.calories) FROM Basket as c")
    List<Basket> sumAllNutritionByUserId();
}

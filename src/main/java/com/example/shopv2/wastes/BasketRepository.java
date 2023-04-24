package com.example.shopv2.wastes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

//    @Query("SELECT sum(c.calories) FROM Basket as c")
//    List<Basket> sumAllNutritionByUserId();

    public List<Basket> findByUserId(Long id);
}

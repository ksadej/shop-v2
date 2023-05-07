package com.example.shopv2.repository;

import com.example.shopv2.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    public ArrayList<Basket> findByIdUser(Long id);
    public ArrayList<Basket> findAllByIdUser(Long id);

}

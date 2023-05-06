package com.example.shopv2.repository;

import com.example.shopv2.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    public ArrayList<Card> findByIdUser(Long id);
    public ArrayList<Card> findAllByIdUser(Long id);

}

package com.example.shopv2.repository;

import com.example.shopv2.model.Basket;
import com.example.shopv2.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    public ArrayList<Basket> findAllByIdUser(Long id);

    @Query("SELECT e FROM Basket e WHERE e.dataAdded >= :dataFrom AND e.dataAdded <= :dataTo")
    List<Basket> findAllByBetweenDate(OffsetDateTime dataFrom, OffsetDateTime dataTo);

    List<Basket> findAllByUserEntity(UserEntity userEntity);

}

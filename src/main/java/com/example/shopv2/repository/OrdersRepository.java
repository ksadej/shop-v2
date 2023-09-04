package com.example.shopv2.repository;

import com.example.shopv2.model.Orders;
import com.example.shopv2.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByUserEntity(UserEntity userEntity);
}

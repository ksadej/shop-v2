package com.example.shopv2.repository;

import com.example.shopv2.model.OrdersStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersStatusRepository extends JpaRepository<OrdersStatus, Long> {
}

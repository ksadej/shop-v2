package com.example.shopv2.repository;

import com.example.shopv2.model.Shipment;
import com.example.shopv2.model.enums.ShipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Shipment findByShipmentType(ShipmentType shipmentType);
}

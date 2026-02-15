package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
    Optional<Shipment> findByShippingNumber(String shippingNumber);
}

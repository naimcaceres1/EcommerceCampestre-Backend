package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarrierRepository extends JpaRepository<Carrier, UUID> {
}

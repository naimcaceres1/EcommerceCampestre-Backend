package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}

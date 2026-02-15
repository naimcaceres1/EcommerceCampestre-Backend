package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Optional<Inventory> findByVariant_VariantId(UUID variantId);
}

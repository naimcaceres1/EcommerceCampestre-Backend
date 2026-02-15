package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.SupplierDeliveryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierDeliveryItemRepository extends JpaRepository<SupplierDeliveryItem, UUID> {
}

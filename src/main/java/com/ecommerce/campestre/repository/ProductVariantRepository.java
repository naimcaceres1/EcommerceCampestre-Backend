package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
}

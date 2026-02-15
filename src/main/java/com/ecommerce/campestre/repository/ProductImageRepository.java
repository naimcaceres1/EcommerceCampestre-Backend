package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
}

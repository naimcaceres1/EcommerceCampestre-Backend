package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}

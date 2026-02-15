package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, UUID> {
    Optional<CustomerOrder> findByOrderNumber(String orderNumber);
}

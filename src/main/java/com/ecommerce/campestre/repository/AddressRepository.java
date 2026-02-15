package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}

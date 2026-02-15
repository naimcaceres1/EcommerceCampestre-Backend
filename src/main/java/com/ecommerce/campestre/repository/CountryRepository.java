package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
}

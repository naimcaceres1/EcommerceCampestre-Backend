package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {
}

package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.UserPhone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserPhoneRepository extends JpaRepository<UserPhone, UUID> {
}

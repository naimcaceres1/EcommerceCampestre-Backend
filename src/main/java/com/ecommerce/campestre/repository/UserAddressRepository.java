package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.UserAddress;
import com.ecommerce.campestre.model.UserAddressId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, UserAddressId> {
}

package com.ecommerce.campestre.service;

import com.ecommerce.campestre.repository.UserPhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPhoneService {

    private final UserPhoneRepository userPhoneRepository;

}

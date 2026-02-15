package com.ecommerce.campestre.service;

import com.ecommerce.campestre.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

}

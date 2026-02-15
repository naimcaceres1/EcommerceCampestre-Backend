package com.ecommerce.campestre.service;

import com.ecommerce.campestre.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

}

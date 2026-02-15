package com.ecommerce.campestre.service;

import com.ecommerce.campestre.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

}

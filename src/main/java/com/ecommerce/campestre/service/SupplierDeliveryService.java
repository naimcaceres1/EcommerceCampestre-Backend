package com.ecommerce.campestre.service;

import com.ecommerce.campestre.repository.SupplierDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierDeliveryService {

    private final SupplierDeliveryRepository supplierDeliveryRepository;

}

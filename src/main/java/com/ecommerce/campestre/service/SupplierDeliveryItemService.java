package com.ecommerce.campestre.service;

import com.ecommerce.campestre.repository.SupplierDeliveryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierDeliveryItemService {

    private final SupplierDeliveryItemRepository supplierDeliveryItemRepository;

}

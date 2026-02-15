package com.ecommerce.campestre.service;

import com.ecommerce.campestre.repository.ApiAuditEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiAuditEventService {

    private final ApiAuditEventRepository apiAuditEventRepository;

}

package com.ecommerce.campestre.service;

import com.ecommerce.campestre.repository.DbAuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbAuditLogService {

    private final DbAuditLogRepository dbAuditLogRepository;

}

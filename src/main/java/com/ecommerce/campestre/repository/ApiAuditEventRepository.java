package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.ApiAuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiAuditEventRepository extends JpaRepository<ApiAuditEvent, Long> {
}

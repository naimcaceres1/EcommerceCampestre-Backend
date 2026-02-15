package com.ecommerce.campestre.repository;

import com.ecommerce.campestre.model.DbAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbAuditLogRepository extends JpaRepository<DbAuditLog, Long> {
}

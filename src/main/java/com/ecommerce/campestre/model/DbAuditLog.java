package com.ecommerce.campestre.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "db_audit_log", schema = "ecommerce")
public class DbAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id", nullable = false, updatable = false)
    private Long auditId;

    @Column(name = "occurred_at", nullable = false, updatable = false)
    private OffsetDateTime occurredAt;

    @Column(name = "action", nullable = false, length = 10)
    private String action;

    @Column(name = "table_name", nullable = false, columnDefinition = "text")
    private String tableName;

    @Column(name = "row_pk", nullable = false, columnDefinition = "text")
    private String rowPk;

    @Column(name = "db_user", nullable = false, columnDefinition = "text")
    private String dbUser;

    @Column(name = "app_user_id")
    private UUID appUserId;

    @Column(name = "request_id", columnDefinition = "text")
    private String requestId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "old_data", columnDefinition = "jsonb")
    private String oldData;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "new_data", columnDefinition = "jsonb")
    private String newData;
}

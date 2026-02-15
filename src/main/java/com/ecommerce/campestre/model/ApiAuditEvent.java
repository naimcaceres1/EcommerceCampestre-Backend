package com.ecommerce.campestre.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "api_audit_event", schema = "ecommerce")
public class ApiAuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false, updatable = false)
    private Long eventId;

    @Column(name = "occurred_at", nullable = false, updatable = false)
    private OffsetDateTime occurredAt;

    @Column(name = "request_id", nullable = false, columnDefinition = "text")
    private String requestId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "http_method", nullable = false, length = 10)
    private String httpMethod;

    @Column(name = "path", nullable = false, columnDefinition = "text")
    private String path;

    @Column(name = "query_string", columnDefinition = "text")
    private String queryString;

    @Column(name = "status_code", nullable = false)
    private Integer statusCode;

    @Column(name = "success", nullable = false)
    private Boolean success;

    @Column(name = "event_name", nullable = false, length = 120)
    private String eventName;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "text")
    private String userAgent;
}

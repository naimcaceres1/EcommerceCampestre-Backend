package com.ecommerce.campestre.model;

import com.ecommerce.campestre.model.enums.StatusGeneral;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_phone", schema = "ecommerce")
public class UserPhone {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "phone_id", nullable = false, updatable = false)
    private UUID phoneId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "number", nullable = false, length = 20)
    private String number;

    @Column(name = "label", nullable = false, length = 30)
    private String label;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @Column(name = "verified_at")
    private OffsetDateTime verifiedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}

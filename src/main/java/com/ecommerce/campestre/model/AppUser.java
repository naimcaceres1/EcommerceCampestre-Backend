package com.ecommerce.campestre.model;

import com.ecommerce.campestre.model.enums.DocumentType;
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
@Table(
        name = "app_user",
        schema = "ecommerce",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_app_user_document", columnNames = {"document_type", "document_number"})
        }
)
public class AppUser {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Column(name = "middle_name", length = 80)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Column(name = "second_last_name", nullable = false, length = 80)
    private String secondLastName;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "citext")
    private String email;

    @Column(name = "password_hash", nullable = false, columnDefinition = "text")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false, length = 20)
    private DocumentType documentType;

    @Column(name = "document_number", nullable = false, length = 30)
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "last_login_at")
    private OffsetDateTime lastLoginAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}

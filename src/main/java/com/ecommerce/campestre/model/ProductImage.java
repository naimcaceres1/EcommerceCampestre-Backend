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
@Table(name = "product_image", schema = "ecommerce")
public class ProductImage {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "image_id", nullable = false, updatable = false)
    private UUID imageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "url", nullable = false, columnDefinition = "text")
    private String url;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "description", columnDefinition = "text")
    private String description;
}

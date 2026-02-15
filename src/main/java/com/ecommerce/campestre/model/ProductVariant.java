package com.ecommerce.campestre.model;

import com.ecommerce.campestre.model.enums.StatusGeneral;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "product_variant",
        schema = "ecommerce",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_variant_combo", columnNames = {"product_id", "color", "size"}),
                @UniqueConstraint(name = "uq_variant_barcode", columnNames = {"barcode"})
        }
)
public class ProductVariant {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "variant_id", nullable = false, updatable = false)
    private UUID variantId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "color", nullable = false, length = 40)
    private String color;

    @Column(name = "size", nullable = false, length = 20)
    private String size;

    @Column(name = "sku", nullable = false, unique = true, length = 64)
    private String sku;

    @Column(name = "barcode", length = 32, unique = true)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;
}

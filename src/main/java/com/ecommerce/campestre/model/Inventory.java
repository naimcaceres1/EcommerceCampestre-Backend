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
@Table(name = "inventory", schema = "ecommerce")
public class Inventory {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "inventory_id", nullable = false, updatable = false)
    private UUID inventoryId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variant_id", nullable = false, unique = true)
    private ProductVariant variant;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "reserved", nullable = false)
    private Integer reserved;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;
}

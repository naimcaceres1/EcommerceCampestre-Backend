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
@Table(name = "product_category", schema = "ecommerce")
public class ProductCategory {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "category_id", nullable = false, updatable = false)
    private UUID categoryId;

    @Column(name = "name", nullable = false, unique = true, length = 80)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;

    @Column(name = "notes", nullable = false, columnDefinition = "text")
    private String notes;
}

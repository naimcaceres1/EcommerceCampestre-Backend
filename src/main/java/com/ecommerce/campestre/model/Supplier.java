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
@Table(name = "supplier", schema = "ecommerce")
public class Supplier {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "supplier_id", nullable = false, updatable = false)
    private UUID supplierId;

    @Column(name = "name", nullable = false, unique = true, length = 120)
    private String name;

    @Column(name = "email", columnDefinition = "citext")
    private String email;

    @Column(name = "phone", length = 25)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;
}

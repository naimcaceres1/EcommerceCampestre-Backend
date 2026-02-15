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
@Table(name = "address", schema = "ecommerce")
public class Address {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "address_id", nullable = false, updatable = false)
    private UUID addressId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "street", nullable = false, length = 120)
    private String street;

    @Column(name = "street_number", nullable = false, length = 20)
    private String streetNumber;

    @Column(name = "apartment", length = 20)
    private String apartment;

    @Column(name = "label", nullable = false, length = 60)
    private String label;

    @Column(name = "postal_code", nullable = false, length = 12)
    private String postalCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;
}

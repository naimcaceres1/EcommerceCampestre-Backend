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
@Table(name = "country", schema = "ecommerce")
public class Country {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "country_id", nullable = false, updatable = false)
    private UUID countryId;

    @Column(name = "name", nullable = false, unique = true, length = 80)
    private String name;

    @Column(name = "phone_code", length = 8)
    private String phoneCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;
}

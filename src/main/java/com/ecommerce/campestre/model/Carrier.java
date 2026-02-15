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
@Table(name = "carrier", schema = "ecommerce")
public class Carrier {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "carrier_id", nullable = false, updatable = false)
    private UUID carrierId;

    @Column(name = "name", nullable = false, unique = true, length = 120)
    private String name;

    @Column(name = "phone", length = 25)
    private String phone;

    @Column(name = "email", columnDefinition = "citext")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;
}

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
        name = "region",
        schema = "ecommerce",
        uniqueConstraints = {@UniqueConstraint(name = "uq_region", columnNames = {"country_id", "name"})}
)
public class Region {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "region_id", nullable = false, updatable = false)
    private UUID regionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;
}

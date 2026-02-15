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
        name = "city",
        schema = "ecommerce",
        uniqueConstraints = {@UniqueConstraint(name = "uq_city", columnNames = {"region_id", "name"})}
)
public class City {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "city_id", nullable = false, updatable = false)
    private UUID cityId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;
}

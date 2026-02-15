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
@Table(name = "supplier_delivery", schema = "ecommerce")
public class SupplierDelivery {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "delivery_id", nullable = false, updatable = false)
    private UUID deliveryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @Column(name = "delivery_time", nullable = false)
    private OffsetDateTime deliveryTime;

    @Column(name = "receipt_number", nullable = false, unique = true, length = 40)
    private String receiptNumber;

    @Column(name = "invoice_number", unique = true, length = 40)
    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusGeneral status;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;
}

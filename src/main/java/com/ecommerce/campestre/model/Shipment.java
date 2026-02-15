package com.ecommerce.campestre.model;

import com.ecommerce.campestre.model.enums.ShipmentStatus;
import com.ecommerce.campestre.model.enums.ShippingMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipment", schema = "ecommerce")
public class Shipment {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "shipment_id", nullable = false, updatable = false)
    private UUID shipmentId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private CustomerOrder order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier;

    @Column(name = "shipping_number", nullable = false, unique = true, length = 30)
    private String shippingNumber;

    @Column(name = "tracking_number", unique = true, length = 60)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false, length = 20)
    private ShippingMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ShipmentStatus status;

    @Column(name = "cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal cost;

    @Column(name = "receiver_name", nullable = false, length = 120)
    private String receiverName;

    @Column(name = "receiver_doc", length = 30)
    private String receiverDoc;

    @Column(name = "courier_name", length = 120)
    private String courierName;

    @Column(name = "courier_doc", length = 30)
    private String courierDoc;

    @Column(name = "dispatched_at")
    private OffsetDateTime dispatchedAt;

    @Column(name = "delivered_at")
    private OffsetDateTime deliveredAt;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;
}

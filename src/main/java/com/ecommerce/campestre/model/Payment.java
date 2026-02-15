package com.ecommerce.campestre.model;

import com.ecommerce.campestre.model.enums.CurrencyCode;
import com.ecommerce.campestre.model.enums.PaymentMethod;
import com.ecommerce.campestre.model.enums.PaymentStatus;
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
@Table(name = "payment", schema = "ecommerce")
public class Payment {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "payment_id", nullable = false, updatable = false)
    private UUID paymentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder order;

    @Column(name = "transaction_ref", nullable = false, unique = true, length = 80)
    private String transactionRef;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false, length = 30)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, length = 10)
    private CurrencyCode currency;

    @Column(name = "amount_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal amountTotal;

    @Column(name = "installments")
    private Short installments;

    @Column(name = "installment_amount", precision = 12, scale = 2)
    private BigDecimal installmentAmount;

    @Column(name = "card_last4", length = 4)
    private String cardLast4;

    @Column(name = "paid_at")
    private OffsetDateTime paidAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "notes", columnDefinition = "text")
    private String notes;
}

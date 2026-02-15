package com.ecommerce.campestre.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "order_item",
        schema = "ecommerce",
        uniqueConstraints = {@UniqueConstraint(name = "uq_order_item_line", columnNames = {"order_id", "line_number"})}
)
public class OrderItem {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "order_item_id", nullable = false, updatable = false)
    private UUID orderItemId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private CustomerOrder order;

    @Column(name = "line_number", nullable = false)
    private Integer lineNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;

    @Column(name = "sku_snapshot", nullable = false, length = 64)
    private String skuSnapshot;

    @Column(name = "color_snapshot", nullable = false, length = 40)
    private String colorSnapshot;

    @Column(name = "size_snapshot", nullable = false, length = 20)
    private String sizeSnapshot;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "discount", nullable = false, precision = 12, scale = 2)
    private BigDecimal discount;

    @Column(name = "line_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal lineTotal;
}

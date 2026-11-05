package Microsserv52.orderservice.model;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private UUID orderId;
    @Column(nullable = false)
    private UUID productId;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal total;

    protected OrderItemEntity() {}

    public OrderItemEntity(UUID id, UUID orderId, UUID productId, Integer quantity, BigDecimal total) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
    }

    public UUID getId() { return id; }
    public UUID getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getTotal() { return total; }
}

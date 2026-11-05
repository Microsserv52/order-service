package Microsserv52.orderservice.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private UUID accountId;
    @Column(nullable = false)
    private OffsetDateTime date;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal total;

    protected OrderEntity() {}

    public OrderEntity(UUID id, UUID accountId, OffsetDateTime date, BigDecimal total) {
        this.id = id;
        this.accountId = accountId;
        this.date = date;
        this.total = total;
    }

    public UUID getId() { return id; }
    public UUID getAccountId() { return accountId; }
    public OffsetDateTime getDate() { return date; }
    public BigDecimal getTotal() { return total; }
}

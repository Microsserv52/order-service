package Microsserv52.orderservice.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findAllByAccountIdOrderByDateDesc(UUID accountId);
    Optional<OrderEntity> findByIdAndAccountId(UUID id, UUID accountId);
}

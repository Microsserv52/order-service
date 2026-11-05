package Microsserv52.orderservice.api;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import Microsserv52.order.OrderController;
import Microsserv52.order.OrderIn;
import Microsserv52.order.OrderOut;
import Microsserv52.order.OrderItemOut;
import Microsserv52.orderservice.model.OrderEntity;
import Microsserv52.orderservice.model.OrderItemEntity;
import Microsserv52.orderservice.model.OrderItemRepository;
import Microsserv52.orderservice.model.OrderRepository;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
public class OrderResource implements OrderController {
    private final OrderRepository repository;
    private final OrderItemRepository itemRepository;
    private final ProductClient productClient;

    public OrderResource(OrderRepository repository, OrderItemRepository itemRepository, ProductClient productClient) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.productClient = productClient;
    }

    @Override
    public OrderOut create(@Valid OrderIn input) {
        UUID accountId = currentAccountId();
        var items = input.items().stream().map(item -> {
            try {
                var product = productClient.findById(item.idProduct());
                BigDecimal lineTotal = product.price().multiply(BigDecimal.valueOf(item.quantity()));
                return new OrderItemEntity(UUID.randomUUID(), null, item.idProduct(), item.quantity(), lineTotal);
            } catch (FeignException exception) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product does not exist");
            }
        }).toList();
        BigDecimal total = items.stream().map(OrderItemEntity::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        OrderEntity saved = repository.save(new OrderEntity(UUID.randomUUID(), accountId, OffsetDateTime.now(), total));
        itemRepository.saveAll(items.stream()
            .map(item -> new OrderItemEntity(item.getId(), saved.getId(), item.getProductId(),
                item.getQuantity(), item.getTotal())).toList());
        return toOutput(saved, "USD");
    }

    @Override
    public List<OrderOut> findAll() {
        UUID accountId = currentAccountId();
        return repository.findAllByAccountIdOrderByDateDesc(accountId).stream()
            .map(order -> toOutput(order, "USD")).toList();
    }

    @Override
    public OrderOut findById(UUID id, String currency) {
        OrderEntity order = repository.findByIdAndAccountId(id, currentAccountId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (!"USD".equalsIgnoreCase(currency)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Currency conversion is unavailable");
        }
        return toOutput(order, currency.toUpperCase());
    }

    private UUID currentAccountId() {
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            return UUID.fromString(subject);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authenticated identity");
        }
    }

    private OrderOut toOutput(OrderEntity order, String currency) {
        List<OrderItemOut> items = itemRepository.findAllByOrderId(order.getId()).stream()
            .map(item -> new OrderItemOut(item.getId(), item.getProductId(), item.getQuantity(), item.getTotal()))
            .toList();
        return new OrderOut(order.getId(), order.getDate(), currency, items, order.getTotal());
    }
}

package Microsserv52.orderservice.api;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product", url = "${platform.product-url:http://product:8080}")
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductOut findById(@PathVariable UUID id);

    record ProductOut(UUID id, BigDecimal price) {}
}

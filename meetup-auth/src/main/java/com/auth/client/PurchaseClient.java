package com.auth.client;

import com.auth.model.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

/**
 * Auth Client.
 *
 * @author David Sapozhnik
 */
@FeignClient(value = "purchase-client", url = "localhost:8083/purchases")
public interface PurchaseClient {

    @PostMapping("/users/{userId}/orders")
    List<OrderDTO> getOrders(@PathVariable UUID userId);
}

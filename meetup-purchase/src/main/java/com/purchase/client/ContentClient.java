package com.purchase.client;

import com.purchase.model.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

/**
 * Content Client.
 *
 * @author David Sapozhnik
 */
@FeignClient(value = "auth-client", url = "localhost:8082/contents")
public interface ContentClient {

    @PostMapping("/products/by-ids")
    List<ProductDTO> getProducts(@RequestBody List<UUID> productIds);
}

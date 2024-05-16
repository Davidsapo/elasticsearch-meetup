package com.auth.client;

import com.auth.enums.UserSort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

/**
 * Auth Client.
 *
 * @author David Sapozhnik
 */
@FeignClient(value = "index-client", url = "localhost:8084/indexes")
public interface IndexClient {

    @GetMapping("/users")
    List<UUID> search(@RequestParam String search,
                      @RequestParam UserSort sort,
                      @RequestParam Direction sortOrder);
}

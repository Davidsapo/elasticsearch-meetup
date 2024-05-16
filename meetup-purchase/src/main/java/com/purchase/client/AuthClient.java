package com.purchase.client;

import com.purchase.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/**
 * Auth Client.
 *
 * @author David Sapozhnik
 */
@FeignClient(value = "auth-client", url = "localhost:8081/auth")
public interface AuthClient {

    @GetMapping("/users/{userId}")
    UserDTO getUser(@PathVariable UUID userId);
}

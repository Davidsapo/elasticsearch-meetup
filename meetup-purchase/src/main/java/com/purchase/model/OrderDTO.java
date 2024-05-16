package com.purchase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.purchase.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author David Sapozhnik
 */
public class OrderDTO {

    private UUID orderId;
    private OrderStatus status;
    private Double amountPaid;
    private UserDTO user;
    private final List<ProductDTO> products = new ArrayList<>();
    private LocalDateTime createdAt;

    @JsonIgnore
    private UUID userId;
    @JsonIgnore
    private final List<UUID> productIds = new ArrayList<>();

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<UUID> getProductIds() {
        return productIds;
    }
}

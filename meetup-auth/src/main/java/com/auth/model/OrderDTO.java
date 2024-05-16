package com.auth.model;

import com.auth.enums.OrderStatus;

import java.util.UUID;

/**
 * @author David Sapozhnik
 */
public class OrderDTO {

    private UUID orderId;
    private OrderStatus status;
    private Double amountPaid;

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

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }
}

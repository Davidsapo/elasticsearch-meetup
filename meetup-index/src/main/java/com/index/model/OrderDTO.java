package com.index.model;

import java.util.UUID;

/**
 * Order DTO.
 *
 * @author David Sapozhnik
 */
public class OrderDTO {

    private UUID orderId;
    private Double amountPaid;
    private String status;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

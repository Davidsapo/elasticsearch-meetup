package com.index.model.events;

import com.index.enums.SystemEventType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Order Created Event.
 *
 * @author David Sapozhnik
 */
public class OrderCreatedEvent extends SystemEvent {

    private UUID userId;
    private String status;
    private Double amountPaid;
    private final List<UUID> productIds = new ArrayList<>();

    public OrderCreatedEvent() {
        super(SystemEventType.ORDER_CREATED);
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public List<UUID> getProductIds() {
        return productIds;
    }
}

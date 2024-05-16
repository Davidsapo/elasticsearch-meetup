package com.purchase.model;

import com.purchase.enums.OrderStatus;
import com.purchase.enums.SystemEventType;

import java.util.UUID;

/**
 * Order Updated Event.
 *
 * @author David Sapozhnik
 */
public class OrderUpdatedEvent extends SystemEvent {

    private UUID userId;
    private OrderStatus status;

    public OrderUpdatedEvent() {
        super(SystemEventType.ORDER_UPDATED);
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

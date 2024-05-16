package com.index.model.events;

import com.index.enums.SystemEventType;

import java.util.UUID;

/**
 * Order Updated Event.
 *
 * @author David Sapozhnik
 */
public class OrderUpdatedEvent extends SystemEvent {

    private UUID userId;
    private String status;

    public OrderUpdatedEvent() {
        super(SystemEventType.ORDER_UPDATED);
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
}

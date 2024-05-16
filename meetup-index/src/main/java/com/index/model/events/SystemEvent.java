package com.index.model.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.index.enums.SystemEventType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * System Event.
 *
 * @author David Sapozhnik
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserCreatedEvent.class, name = "USER_CREATED"),
        @JsonSubTypes.Type(value = UserUpdatedEvent.class, name = "USER_UPDATED"),
        @JsonSubTypes.Type(value = ProductCreatedEvent.class, name = "PRODUCT_CREATED"),
        @JsonSubTypes.Type(value = OrderCreatedEvent.class, name = "ORDER_CREATED"),
        @JsonSubTypes.Type(value = OrderUpdatedEvent.class, name = "ORDER_UPDATED")
})
public class SystemEvent implements Serializable {

    protected UUID entityId;
    protected LocalDateTime createdAt;
    protected SystemEventType type;

    public SystemEvent(SystemEventType type) {
        this.type = type;
    }

    public SystemEventType getType() {
        return type;
    }

    public void setType(SystemEventType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }
}

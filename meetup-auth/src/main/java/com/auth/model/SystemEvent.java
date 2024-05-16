package com.auth.model;

import com.auth.enums.SystemEventType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * System Event.
 *
 * @author David Sapozhnik
 */
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

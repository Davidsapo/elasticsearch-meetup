package com.index.service;

import com.index.enums.SystemEventType;
import com.index.model.events.SystemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * System Event Service.
 *
 * @author David Sapozhnik
 */
public abstract class SystemEventService {

    protected static final Logger LOG = LoggerFactory.getLogger(SystemEventService.class);

    /**
     * Get primary event types.
     *
     * @return {@link SystemEventType} type of the event service is created for
     */
    public abstract List<SystemEventType> getPrimaryEventTypes();

    /**
     * Get additional event types.
     *
     * @return {@link SystemEventType} type of the event service is created for
     */
    public abstract List<SystemEventType> getAdditionalEventTypes();

    /**
     * Process system event.
     *
     * @param systemEvent {@link SystemEvent} extended object
     */
    public abstract <T extends SystemEvent> void processSystemEvent(T systemEvent);
}

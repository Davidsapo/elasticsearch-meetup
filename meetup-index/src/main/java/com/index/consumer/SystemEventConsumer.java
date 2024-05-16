package com.index.consumer;

import com.index.enums.SystemEventType;
import com.index.model.events.SystemEvent;
import com.index.service.SystemEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * System Event Listener.
 *
 * @author David Sapozhnik
 */
@Component
public class SystemEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SystemEventConsumer.class);

    @Autowired
    private List<SystemEventService> services;

    private final Map<SystemEventType, List<SystemEventService>> primaryServiceMap = new HashMap<>();
    private final Map<SystemEventType, List<SystemEventService>> additionalServiceMap = new HashMap<>();

    @PostConstruct
    private void init() {
        services.forEach(systemEventService -> {
                    systemEventService.getPrimaryEventTypes().forEach(systemEventType ->
                            prepareServices(systemEventType, systemEventService, primaryServiceMap));
                    systemEventService.getAdditionalEventTypes().forEach(systemEventType ->
                            prepareServices(systemEventType, systemEventService, additionalServiceMap));
                }
        );
    }

    /**
     * Receiving the message object from RabbitMQ.
     *
     * @param systemEvent {@link SystemEvent} message object
     */
    @RabbitListener(queues = "${index.queue.name}", errorHandler = "messageExceptionHandler")
    public <T extends SystemEvent> void receiveMessage(@Payload T systemEvent) {

        LOG.debug("Receive {} system event, entity id='{}'.", systemEvent.getType(), systemEvent.getEntityId());
        var primaryServices = primaryServiceMap.get(systemEvent.getType());
        if (nonNull(primaryServices)) {
            primaryServices.forEach(service -> service.processSystemEvent(systemEvent));
        }
        var additionalServices = additionalServiceMap.get(systemEvent.getType());
        if (nonNull(additionalServices)) {
            additionalServices.forEach(service -> service.processSystemEvent(systemEvent));
        }
    }

    /* Private methods */

    private void prepareServices(SystemEventType systemEventType,
                                 SystemEventService systemEventService,
                                 Map<SystemEventType, List<SystemEventService>> serviceMap) {
        var services = serviceMap.get(systemEventType);
        if (isNull(services)) {
            services = new ArrayList<>();
            serviceMap.put(systemEventType, services);
        }
        services.add(systemEventService);
    }
}
package com.purchase.listener;

import com.purchase.entity.Order;
import com.purchase.model.OrderCreatedEvent;
import com.purchase.model.OrderUpdatedEvent;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

/**
 * Order Listener.
 *
 * @author David Sapozhnik
 */
public class OrderListener {

    @Autowired
    private RabbitTemplate eventTemplate;

    @Value("${auth.queue.exchange}")
    private String exchangeName;

    @Value("${auth.queue.name}")
    private String queueName;

    @PostPersist
    private void afterSave(Order order) {
        var orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setCreatedAt(order.getCreatedAt());
        orderCreatedEvent.setEntityId(order.getOrderId());
        orderCreatedEvent.setStatus(order.getStatus());
        orderCreatedEvent.setUserId(order.getUserId());
        orderCreatedEvent.setAmountPaid(order.getAmountPaid());
        orderCreatedEvent.getProductIds().addAll(order.getProductIds());
        eventTemplate.convertAndSend(exchangeName, queueName, orderCreatedEvent);
    }

    @PostUpdate
    private void afterUpdate(Order order) {
        var orderUpdatedEvent = new OrderUpdatedEvent();
        orderUpdatedEvent.setCreatedAt(LocalDateTime.now());
        orderUpdatedEvent.setEntityId(order.getOrderId());
        orderUpdatedEvent.setUserId(order.getUserId());
        orderUpdatedEvent.setStatus(order.getStatus());
        eventTemplate.convertAndSend(exchangeName, queueName, orderUpdatedEvent);
    }
}

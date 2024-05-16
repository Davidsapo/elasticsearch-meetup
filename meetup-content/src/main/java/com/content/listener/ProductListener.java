package com.content.listener;

import com.content.entity.Product;
import com.content.model.ProductCreatedEvent;
import jakarta.persistence.PostPersist;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Product Listener.
 *
 * @author David Sapozhnik
 */
public class ProductListener {

    @Autowired
    private RabbitTemplate eventTemplate;

    @Value("${auth.queue.exchange}")
    private String exchangeName;

    @Value("${auth.queue.name}")
    private String queueName;

    @PostPersist
    private void afterSave(Product product) {
        var userEvent = new ProductCreatedEvent();
        userEvent.setCreatedAt(product.getCreatedAt());
        userEvent.setEntityId(product.getProductId());
        userEvent.setPrice(product.getPrice());
        userEvent.setName(product.getName());
        eventTemplate.convertAndSend(exchangeName, queueName, userEvent);
    }
}

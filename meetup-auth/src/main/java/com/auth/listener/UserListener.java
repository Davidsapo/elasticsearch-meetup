package com.auth.listener;

import com.auth.entity.User;
import com.auth.model.UserCreatedEvent;
import com.auth.model.UserUpdatedEvent;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

/**
 * User Listener.
 *
 * @author David Sapozhnik
 */
public class UserListener {

    @Autowired
    private RabbitTemplate eventTemplate;

    @Value("${auth.queue.exchange}")
    private String exchangeName;

    @Value("${auth.queue.name}")
    private String queueName;

    @PostPersist
    private void afterSave(User user) {
        var userEvent = new UserCreatedEvent();
        userEvent.setCreatedAt(user.getCreatedAt());
        userEvent.setEntityId(user.getUserId());
        userEvent.setEmail(user.getEmail());
        userEvent.setFullName(user.getFullName());
        userEvent.setPhone(user.getPhone());
        eventTemplate.convertAndSend(exchangeName, queueName, userEvent);
    }

    @PostUpdate
    private void afterUpdate(User user) {
        var userEvent = new UserUpdatedEvent();
        userEvent.setCreatedAt(LocalDateTime.now());
        userEvent.setEntityId(user.getUserId());
        userEvent.setEmail(user.getEmail());
        userEvent.setFullName(user.getFullName());
        userEvent.setPhone(user.getPhone());
        eventTemplate.convertAndSend(exchangeName, queueName, userEvent);
    }
}

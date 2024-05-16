package com.index.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Message Exception Handler.
 *
 * @author David Sapozhnik
 */
@Component
public class MessageExceptionHandler implements RabbitListenerErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MessageExceptionHandler.class);

    @Override
    public Object handleError(Message message, org.springframework.messaging.Message<?> message1,
                              ListenerExecutionFailedException e) {
        var exception = findException(e);
        var strMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        LOG.error("Error during message receiving. {} - {}:\n {}.\n {}",
                exception.getClass().getName(),
                exception.getMessage(),
                strMessage,
                message.getMessageProperties());
        return null;
    }

    private Throwable findException(Throwable e) {
        if (e instanceof AmqpException) {
            return findException(e.getCause());
        }
        return e;
    }
}
package com.petproject.rabbitmq.consumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ErrorQueueConsumer {

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "error-queue", durable = "true"),
        exchange = @Exchange(value = "september", type = ExchangeTypes.TOPIC),
        key = "error-key"
    ))
    public void processErrorQueueMessage(String message) {
        System.out.println();
        System.out.println("Received from error-queue: " + message);
        System.out.println();
    }
}

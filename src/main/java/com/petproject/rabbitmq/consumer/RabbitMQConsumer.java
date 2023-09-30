package com.petproject.rabbitmq.consumer;

import com.petproject.model.User;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableRabbit
public class RabbitMQConsumer {

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "september-queue", durable = "true"),
        exchange = @Exchange(value = "september", type = ExchangeTypes.TOPIC),
        key = "september-key"
    ))
    public void processMyQueue(final List<User> users) {
        System.out.printf("Received from september-queue : %s ", users);
    }
}

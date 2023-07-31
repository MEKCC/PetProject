package com.petproject.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQConsumer {


    @RabbitListener(queues = "queue-mp")
    public void processMyQueue(String message) {
        System.out.printf("Received from queue-mp : %s ", new String(message.getBytes()));
    }
}

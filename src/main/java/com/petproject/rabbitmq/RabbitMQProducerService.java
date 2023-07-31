package com.petproject.rabbitmq;

public interface RabbitMQProducerService {

    void sendMessage(String message, String routingKey);
}

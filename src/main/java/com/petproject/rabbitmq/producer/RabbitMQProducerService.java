package com.petproject.rabbitmq.producer;

import com.petproject.rabbitmq.model.UserDto;

import java.util.List;

public interface RabbitMQProducerService {

    void sendMessage(final List<UserDto> users, final String routingKey);
}

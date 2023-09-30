package com.petproject.rabbitmq.producer;

import com.petproject.rabbitmq.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RabbitMQProducerServiceImpl implements RabbitMQProducerService {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(final List<UserDto> users, final String routingKey) {
        rabbitTemplate.convertAndSend("september", routingKey, users);
    }
}

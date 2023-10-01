package com.petproject.rabbitmq.controller;

import com.petproject.rabbitmq.producer.RabbitMQProducerService;
import com.petproject.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RabbitController {

    private final RabbitMQProducerService rabbitMQProducerService;
    private final UserRepository userRepository;

    @GetMapping("/send")
    public void sendMessageToRabbit() {
        try {
            rabbitMQProducerService.sendMessage(userRepository.findAllUsers(), "september-key");

            // Генеруємо приклад помилки
            throw new RuntimeException("some error");

        } catch (Exception e) {
            rabbitMQProducerService.sendMessage(e.getMessage(), "error-key");
        }
    }
}

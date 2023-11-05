package com.petproject.rabbitmq.controller;

import com.petproject.rabbitmq.model.UserDto;
import com.petproject.rabbitmq.producer.RabbitMQProducerService;
import com.petproject.user.UserRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static java.util.Collections.singletonList;

@RestController
@AllArgsConstructor
public class RabbitController {

    private final RabbitMQProducerService rabbitMQProducerService;
    private final UserRepository userRepository;
    private final MeterRegistry meterRegistry;

    @GetMapping("/send")
    public void sendMessageToRabbit() {
        try {
            // Отримайте об'єкт для метрики HTTP-запитів
            var httpRequestsCounter = meterRegistry.counter("http.server.request", "endpoint", "/send");

            // Збільшуйте лічильник для кожного HTTP-запиту
            httpRequestsCounter.increment();

            rabbitMQProducerService.sendMessage(singletonList(new UserDto("rabbitmqName", "rabbitmqEmail")), "september-key");

            // Генеруємо приклад помилки
            throw new RuntimeException("some error");

        } catch (Exception e) {
            rabbitMQProducerService.sendMessage(e.getMessage(), "error-key");
        }
    }
}

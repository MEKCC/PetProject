package com.petproject.rabbitmq.controller;

import com.petproject.rabbitmq.producer.RabbitMQProducerService;
import com.petproject.repo.UserRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

            rabbitMQProducerService.sendMessage(userRepository.findAllUsers(), "september-key");

            // Генеруємо приклад помилки
            throw new RuntimeException("some error");

        } catch (Exception e) {
            rabbitMQProducerService.sendMessage(e.getMessage(), "error-key");
        }
    }
}

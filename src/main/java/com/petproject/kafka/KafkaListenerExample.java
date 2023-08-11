package com.petproject.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerExample {

    @KafkaListener(topics = "test-topic", groupId = "myGroup")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}


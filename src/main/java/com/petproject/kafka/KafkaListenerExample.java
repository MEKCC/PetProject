package com.petproject.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerExample {

    @KafkaListener(topics = "demo_topic", groupId = "myGroup")
    void listener(final Message data) {
        System.out.println(data);
    }
}

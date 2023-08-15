package com.petproject.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSenderExample {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    public KafkaSenderExample(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(final Message message, final String topicName) {
        kafkaTemplate.send(topicName, message);
    }
}

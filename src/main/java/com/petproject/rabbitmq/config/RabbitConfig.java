package com.petproject.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue myQueue() {
        return new Queue("september-queue");
    }

    @Bean
    public Exchange exchange() {
        return new TopicExchange("september", true, false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
            .bind(myQueue())
            .to(exchange())
            .with("september-key")
            .noargs();
    }
}

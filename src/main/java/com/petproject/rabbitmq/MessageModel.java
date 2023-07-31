package com.petproject.rabbitmq;

import lombok.Data;

@Data
public class MessageModel {

    private String message;
    private String routingKey;
}

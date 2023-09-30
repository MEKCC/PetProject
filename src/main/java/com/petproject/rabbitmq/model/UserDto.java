package com.petproject.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserDto implements Serializable {

    private String userName;
    private String email;
}

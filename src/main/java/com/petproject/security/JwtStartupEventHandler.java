package com.petproject.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class JwtStartupEventHandler implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${c2f.jwt.secret}")
    private String secret;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        JWTConsumer.setSec("HS256", secret);
    }

}
package com.petproject;

import java.time.Clock;
import java.time.Instant;

import com.petproject.service.OtpService;
import com.petproject.validation.ConfigurableListableBeanFactoryHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static java.time.ZoneOffset.UTC;

@TestConfiguration
public class TestConfig {

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.parse("2007-12-03T10:15:30.00Z"), UTC);
    }

    @Bean
    public OtpService otpService() {
        return () -> "1583830859581-d8ac49aa-9e8b-4def-921b-0a5a5a73ba3a";
    }

    @Bean
    public ConfigurableListableBeanFactoryHolder configurableListableBeanFactoryHolder(
        final ConfigurableListableBeanFactory factory
    ) {
        return new ConfigurableListableBeanFactoryHolder(factory);
    }
}

package com.petproject;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;

public final class ClassPathResourceReader {

    @SneakyThrows
    public static String getResource(final String path) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(
            new ClassPathResource(path, ClassPathResourceReader.class.getClassLoader()).getInputStream(), UTF_8))
        ) {
            return reader.lines().collect(joining("\n"));
        }
    }
}

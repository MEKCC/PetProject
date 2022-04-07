package com.petproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AuthorController {

    @GetMapping(value = "/authors", produces = APPLICATION_JSON_VALUE)
    public String getAuthors() {
        return "Hello World, Docker";
    }
}
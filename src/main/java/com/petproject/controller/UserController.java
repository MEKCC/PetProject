package com.petproject.controller;

import com.petproject.model.User;
import com.petproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    public List<User> getAuthors() {
        log.info("@@@ <--- !!! SOME INFO !!! ---> @@@");
        return userService.getAllUsers();
    }

    @GetMapping("/cached-user")
    public User findStudentById() {
        log.info("Searching by ID: ");
        return userService.getUserById();
    }
}
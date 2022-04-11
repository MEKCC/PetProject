package com.petproject.controller;

import com.petproject.model.User;
import com.petproject.repo.UserRepository;
import com.petproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    public List<User> getAuthors() {
        return userService.getAllUsers();
    }
}
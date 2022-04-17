package com.petproject.service;

import com.petproject.model.Role;
import com.petproject.model.User;
import com.petproject.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //    value - имя кеша, cacheManager - бин который им управляет
    @Cacheable(value = "cachedUser", cacheManager = "temporaryCacheManager")
    public User getUserById() {
        try {
            log.info("Going to sleep for 5 Secs.. to simulate backend call.");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new User()
                .setId(3L)
                .setUsername("cached name")
                .setEmail("cached email")
                .setPassword("cached password")
                .setRoles(List.of(
                        new Role().setId(1L).setRoleName("ADMIN"),
                        new Role().setId(2L).setRoleName("USER")
                ));
    }
}
package com.petproject.service;

import com.petproject.common.model.NotFoundException;
import com.petproject.common.model.UserException;
import com.petproject.logger.C2FLogger;
import com.petproject.model.User;
import com.petproject.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.lang.Thread.sleep;

@Service
public class UserService {

    private static final Integer MILLISECONDS_TO_SLEEP = 2000;
    private final C2FLogger log = new C2FLogger();

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //    value - имя кеша, cacheManager - бин который им управляет
    @Cacheable(value = "cachedUser", cacheManager = "temporaryCacheManager")
    public User getUserById() {
        try {
            log.info("1", C2FLogger.LogType.GENERAL, () -> format("thread will sleep during %s milliseconds", MILLISECONDS_TO_SLEEP));
            sleep(MILLISECONDS_TO_SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new User()
            .setId(3L)
            .setUsername("cached name")
            .setEmail("cached email")
            .setPassword("cached password");
    }

    public User getNonExistUser() {
        final Optional<User> optionalUser = userRepository.findById(999999L);
        if (optionalUser.isEmpty()) {
            throw new UserException("user not exist");
        } else {
            return optionalUser.get();
        }
    }

    public User getNonExistUserVoidResponse() {
        final Optional<User> optionalUser = userRepository.findById(999999L);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException();
        } else {
            return optionalUser.get();
        }
    }
}

package com.petproject.repo;

import com.petproject.model.User;
import com.petproject.rabbitmq.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    @Query("select new com.petproject.rabbitmq.model.UserDto(u.username, u.email) from User u")
    List<UserDto> findAllUsers();
}

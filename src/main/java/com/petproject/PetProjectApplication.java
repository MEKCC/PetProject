package com.petproject;

import com.petproject.auth.AuthenticationService;
import com.petproject.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.petproject.user.Role.ADMIN;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PetProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
        AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                .firstname("Admin")
                .lastname("Admin")
                .email("admin@mail.com")
                .password("password")
                .role(ADMIN)
                .build();
            service.registerAdmin(admin);

        };
    }
}

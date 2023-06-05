package com.petproject.csvsecurity;

import com.petproject.security.AuthenticationJdbcRepository;
import com.petproject.security.C2fSecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpMethod.GET;

@Component
@Order(200)
public class UserSecurityConfiguration extends C2fSecurityConfiguration {

    @Value("${c2f.roles.admin.name}")
    private String adminRole;
    @Value("${c2f.roles.edit.user.name}")
    private String editUser;

    @Autowired
    private AuthenticationJdbcRepository authenticationJdbcRepository;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
//            .antMatchers(GET, "/users").access("@userWebSecurity.isUserExists(principal)")
            .antMatchers(GET, "/users").hasAuthority(adminRole)
            .antMatchers(GET, "/cached-user").hasAnyAuthority(adminRole, editUser)
        ;

        log("USER");
    }

    @Bean
    public UserWebSecurity userWebSecurity() {
        return new UserWebSecurity(authenticationJdbcRepository);
    }
}
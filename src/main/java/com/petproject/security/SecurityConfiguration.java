package com.petproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import static java.util.Collections.emptyList;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.petproject.security")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private UserCache userCache;
    @Autowired
    private List<C2fSecurityConfiguration> securityConfigurerAdapters;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        for (final C2fSecurityConfiguration securityConfigurerAdapter : securityConfigurerAdapters) {
            securityConfigurerAdapter.configure(http);
        }

        final JwtTokenAuthenticationFilter filter = new JwtTokenAuthenticationFilter(
            authenticationManagerBean(), new PermitAllRequestMatcher(emptyList())
        );
        jwtAuthenticationProvider.setUserCache(userCache);
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .addFilterBefore(new CORSFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(jwtAuthenticationProvider)
            .exceptionHandling()
            .authenticationEntryPoint(new FailAuthenticationEntryPoint())
        ;
    }

}

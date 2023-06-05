package com.petproject.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public final class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationProvider(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void additionalAuthenticationChecks(
        final UserDetails userDetails, final UsernamePasswordAuthenticationToken authentication
    ) {
        // We don't need to additional authentication checks
//        System.out.println("DETAILS");
    }

    @Override
    protected UserDetails retrieveUser(
        final String username, final UsernamePasswordAuthenticationToken authentication
    ) {
        return userDetailsService.loadUserByUsername(username);
    }

}
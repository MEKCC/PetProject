package com.petproject.csvsecurity;

import com.petproject.security.AuthenticatedAccount;
import com.petproject.security.AuthenticationJdbcRepository;

public class UserWebSecurity {

    private final AuthenticationJdbcRepository authenticationJdbcRepository;

    public UserWebSecurity(final AuthenticationJdbcRepository authenticationJdbcRepository) {
        this.authenticationJdbcRepository = authenticationJdbcRepository;
    }

    /**
     * Called for authenticated user.
     *
     * @param principal
     * @return
     */
    public boolean isUserExists(final AuthenticatedAccount principal) {
        return authenticationJdbcRepository.isUserExists(principal.getUsername());
    }

    /**
     * Called for anonymous user.
     *
     * @param principal 'anonymous'
     */
    public boolean isUserExists(final String principal) {
        return false;
    }
}
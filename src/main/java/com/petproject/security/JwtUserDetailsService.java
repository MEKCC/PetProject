package com.petproject.security;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

import static java.text.MessageFormat.format;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final AuthenticationJdbcRepository jdbcRepository;
    private final Clock clock;

    public JwtUserDetailsService(final AuthenticationJdbcRepository jdbcRepository, final Clock clock) {
        this.jdbcRepository = jdbcRepository;
        this.clock = clock;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final DecodedToken decodedToken = new DecodedToken(new JWTConsumer(username));
        final Integer userId = decodedToken.getUserId();
        try {
            final AuthenticatedAccount authenticatedAccount = jdbcRepository.fetchAuthenticatedAccount(userId);
            final String[] tokenPermissions = decodedToken.getPermissions().toArray(new String[] {});
            final List<GrantedAuthority> tokenAuthorities = AuthorityUtils.createAuthorityList(tokenPermissions);
            authenticatedAccount.getAuthorities().addAll(tokenAuthorities);
            return authenticatedAccount
                .toBuilder()
                .token(username)
                .credentialsNonExpired(LocalDateTime.now(clock).isBefore(decodedToken.getExpired()))
                .enabled(decodedToken.getActive())
                .build();
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException(format("Account id = {0} not found.", userId));
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UsernameNotFoundException(format("Account id = {0} has {1} records.", userId,
                e.getActualSize()
            ));
        }
    }
}
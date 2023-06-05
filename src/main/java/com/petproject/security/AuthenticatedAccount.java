package com.petproject.security;

import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder(toBuilder = true)
@Accessors(chain = true)
@Getter
public class AuthenticatedAccount implements UserDetails, Cloneable {

    private final String firstName;
    private final String token;
    private final String userName;
    private final Collection<GrantedAuthority> authorities;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    @Override
    public String getPassword() {
        return null;
    }

//    @Override
//    public String getUsername() {
//        return token;
//    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public String getBearerToken() {
        if (token == null || token.startsWith("Bearer ")) {
            return token;
        } else {
            return "Bearer " + token;
        }
    }

    @SneakyThrows
    @Override
    public AuthenticatedAccount clone() {
        return (AuthenticatedAccount) super.clone();
    }
}

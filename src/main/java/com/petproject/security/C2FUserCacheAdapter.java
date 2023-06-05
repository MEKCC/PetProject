package com.petproject.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class C2FUserCacheAdapter implements UserCache {
    private final MemoryCache<String, UserDetails> cache;

    public C2FUserCacheAdapter(@Value("${c2f.cache.timeout}") final Long cacheTimeout) {
        this.cache = new MemoryCache<>(cacheTimeout, true);
    }

    @Override
    public UserDetails getUserFromCache(final String username) {
        return cache.load(username);
    }

    @Override
    public void putUserInCache(final UserDetails user) {
        cache.store(user.getUsername(), user);
    }

    @Override
    public void removeUserFromCache(final String username) {
        cache.remove(username);
    }
}

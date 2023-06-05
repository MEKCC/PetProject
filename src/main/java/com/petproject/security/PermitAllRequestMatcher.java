package com.petproject.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public final class PermitAllRequestMatcher implements RequestMatcher {

    private final List<String> endpoints;

    public PermitAllRequestMatcher(final List<String> endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    public boolean matches(final HttpServletRequest request) {
        return endpoints.stream().anyMatch(endpoint -> new AntPathRequestMatcher(endpoint).matches(request));
    }

}
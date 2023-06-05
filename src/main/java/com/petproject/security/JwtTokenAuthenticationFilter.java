package com.petproject.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public final class JwtTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public JwtTokenAuthenticationFilter(
        final AuthenticationManager authenticationManager, final RequestMatcher permitMatcher
    ) {
        super(permitMatcher);
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(new RestAuthenticationSuccessHandler());
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        final String token = request.getHeader(AUTHORIZATION);
        final DecodedToken decodedToken = new DecodedToken(new JWTConsumer(token));
        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            token,
            decodedToken,
            decodedToken.getPermissions().stream().map(SimpleGrantedAuthority::new).collect(toList())
        );
        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(
        final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain,
        final Authentication authResult
    ) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }

    @Override
    protected boolean requiresAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        response.addHeader("acceptedAt", String.valueOf(System.nanoTime()));
        return request.getHeader(AUTHORIZATION) != null;
    }

}

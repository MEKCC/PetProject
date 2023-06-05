package com.petproject.security;

import com.petproject.logger.C2FLogger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.petproject.logger.C2FLogger.LogType.SYSTEM;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public final class FailAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final C2FLogger logger = new C2FLogger();

    @Override
    public void commence(
        final HttpServletRequest request, final HttpServletResponse response,
        final AuthenticationException authException
    ) {
        logger.error(
            null,
            SYSTEM,
            authException,
            () -> format("Authentication failed.%n Request: %s.%n Message: %s.%n",
                request.getRequestURI(),
                authException.getMessage()
            )
        );
        response.setStatus(UNAUTHORIZED.value());
        response.setHeader("Message", "Full authentication is required to access this resource");
    }
}

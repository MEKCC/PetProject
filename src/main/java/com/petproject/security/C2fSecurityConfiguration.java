package com.petproject.security;

import com.petproject.logger.C2FLogger;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.petproject.logger.C2FLogger.LogType.GENERAL;

public abstract class C2fSecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected final C2FLogger logger = new C2FLogger();
    private boolean logged = false;

    /**
     * Overrides to make public.
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(final HttpSecurity http) throws Exception {
    }

    protected final void log(final String securityName) {
        if (!logged) {
            logged = true;
            logger.info("boot", GENERAL, () -> securityName + " security activated");
        }
    }
}
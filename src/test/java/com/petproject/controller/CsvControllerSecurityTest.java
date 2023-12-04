package com.petproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.AbstractControllerSecurityTest;
import com.petproject.auth.AuthenticationRequest;
import com.petproject.auth.AuthenticationService;
import com.petproject.security.JwtService;
import com.petproject.service.CsvService;
import com.petproject.token.TokenRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CsvController.class)
public final class CsvControllerSecurityTest extends AbstractControllerSecurityTest {

    @MockBean
    private TokenRepository tokenRepository;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private CsvService csvService;
    @MockBean
    private JwtService jwtService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final TestingAuthenticationToken wrongPermissionUserDetails = new TestingAuthenticationToken(
        new AuthenticationRequest("wrong@gmail.com", "password"),
        null
    );

    @Nested
    class FetchUsers {

        @Test
        void forbidForWrongPermission() throws Exception {
            mvc.perform(get("/export")
                    .accept(APPLICATION_JSON)
                    .with(authentication(wrongPermissionUserDetails)))
                .andDo(print())
                .andExpect(status().isForbidden());
        }

        @Test
        void unauthorizedForAnonymousUser() throws Exception {

            mvc
                .perform(get("/export").accept(APPLICATION_JSON).with(anonymous()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
        }

    }
}

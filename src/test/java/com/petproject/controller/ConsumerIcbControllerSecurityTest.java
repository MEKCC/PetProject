package com.petproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petproject.AbstractControllerSecurityTest;
import com.petproject.security.AuthenticatedAccount;
import com.petproject.security.AuthenticationJdbcRepository;
import com.petproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class)
public final class ConsumerIcbControllerSecurityTest extends AbstractControllerSecurityTest {

    @MockBean
    public AuthenticationJdbcRepository authenticationJdbcRepository;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final AuthenticatedAccount wrongPermissionUserDetails = AuthenticatedAccount
        .builder()
        .userName("wrong user name")
        .firstName("wrong name")
        .authorities(createAuthorityList("Wrong permission"))
        .build();

    private AuthenticatedAccount correctUserDetails;

    @BeforeEach
    public void setUp() {
        correctUserDetails = AuthenticatedAccount
            .builder()
            .token("TOKEN")
            .userName("MEKCC")
            .authorities(createAuthorityList("Administrator"))
            .build();
    }

    @Nested
    class FetchUsers {

        @Test
        void forbidForWrongPermission() throws Exception {

            mvc
                .perform(get("/users")
                    .accept(APPLICATION_JSON)
                    .with(user(wrongPermissionUserDetails)))
                .andDo(print())
                .andExpect(status().isForbidden());
        }

        @Test
        void unauthorizedForAnonymousUser() throws Exception {

            mvc
                .perform(get("/users").accept(APPLICATION_JSON).with(anonymous()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
        }

    }
}

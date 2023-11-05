package com.petproject.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.petproject.AbstractControllerIntegrationTest;
import com.petproject.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.petproject.ClassPathResourceReader.getResource;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TestConfig.class)
class MyUserControllerTest extends AbstractControllerIntegrationTest {

    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Im1ha3N5bSIsImV4cCI6MTczNTY4OTU5OSwiaWQiOjEsImRhdGEiOjE2NzI1MzEyMDAsInVzZXJuYW1lIjoibWFrc3ltX3AifQ.1AlaxxQb_ZXFU8gJlfUWW6vZKpoe_nzD_0pMJnN4IoE";

    @Autowired
    private MockMvc mvc;

    @Test
    @DataSet(value = "datasets/getUsers_init.xml", disableConstraints = true)
    void getUsers() throws Exception {

        mvc
            .perform(get("/users")
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, TOKEN))
            .andExpect(status().isOk())
            .andExpect(content().json(getResource("db/changelog/json/getUsers_response.json")));

    }

}

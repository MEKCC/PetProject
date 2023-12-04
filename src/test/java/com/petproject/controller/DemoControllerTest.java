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
class DemoControllerTest extends AbstractControllerIntegrationTest {

    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZmlyc3RuYW1lIjoiTUVLQ0MiLCJsYXN0bmFtZSI6IlBldHIiLCJlbWFpbCI6Im1ha3NAZ21haWwuY29tIiwiZXhwIjoxNzM1Njg5NTk5LCJpZCI6MSwiZGF0YSI6MTY3MjUzMTIwMCwicm9sZSI6IkFETUlOIn0.-i3JzTWVAGhhNJ3kZkfEh8OSuFiXwnj1JNET-C_gUsQ";

    @Autowired
    private MockMvc mvc;

//    @Test
//    @DataSet(value = "datasets/getUsers_init.xml", disableConstraints = true)
//    void getHello() throws Exception {
//
//        mvc
//            .perform(get("/api/v1/demo-controller")
//                .accept(APPLICATION_JSON)
//                .header(AUTHORIZATION, TOKEN))
//            .andExpect(status().isOk())
//            .andExpect(content().json(getResource("db/changelog/json/getUsers_response.json")));
//
//    }

}

package io.github.stanleyhh.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getMe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/auth")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "url")
                        )))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.attributes.login").value("testUser"))
                .andExpect(jsonPath("$.attributes.avatar_url").value("url"));
    }
}
package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.repositories.GenreRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GenreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void getAllGenres_shouldReturnAllGenres() throws Exception {
        Genre action = Genre.builder().name("action").build();
        Genre fantasy = Genre.builder().name("fantasy").build();
        genreRepository.saveAll(List.of(action, fantasy));

        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[*].name", Matchers.containsInAnyOrder("action", "fantasy")));
    }
}
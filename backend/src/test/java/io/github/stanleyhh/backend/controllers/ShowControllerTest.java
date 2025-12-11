package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.entities.Country;
import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.ShowStatus;
import io.github.stanleyhh.backend.repositories.CountryRepository;
import io.github.stanleyhh.backend.repositories.GenreRepository;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ShowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CountryRepository countryRepository;

    Genre action;
    Genre fantasy;
    Country germany;
    Country usa;

    @BeforeEach
    void setup() {
        LocalDate date = LocalDate.now();
        showRepository.deleteAll();
        genreRepository.deleteAll();
        countryRepository.deleteAll();

        action = genreRepository.save(Genre.builder().name("action").build());
        fantasy = genreRepository.save(Genre.builder().name("fantasy").build());
        germany = countryRepository.save(Country.builder().name("Germany").build());
        usa = countryRepository.save(Country.builder().name("USA").build());

        Show s1 = Show.builder()
                .title("Show A")
                .originalTitle("Show A")
                .firstAirDate(date)
                .lastAirDate(date)
                .imageUrl("https://image.png")
                .status(ShowStatus.ONGOING)
                .genres(Set.of(action, fantasy))
                .countries(Set.of(germany, usa))
                .build();

        Show s2 = Show.builder()
                .title("Show B")
                .originalTitle("Show B")
                .firstAirDate(date)
                .lastAirDate(date)
                .imageUrl("https://image.png")
                .status(ShowStatus.ONGOING)
                .genres(Set.of(action, fantasy))
                .countries(Set.of(germany, usa))
                .build();

        showRepository.save(s1);
        showRepository.save(s2);
    }

    @Test
    void searchShows_shouldReturnPagedResponse() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("page", "1")
                        .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("Show A"))
                .andExpect(jsonPath("$.content[0].countries").value(hasItem(germany.getId().toString())))
                .andExpect(jsonPath("$.content[0].genres").value(hasItem(action.getId().toString())))
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void searchShows_shouldRespectPagination() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("page", "1")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.page").value(1))
                .andExpect(jsonPath("$.size").value(1))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(2));
    }
}
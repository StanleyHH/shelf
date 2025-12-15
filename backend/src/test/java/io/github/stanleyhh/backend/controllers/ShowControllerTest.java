package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.entities.Country;
import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.enums.ShowStatus;
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

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ShowControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CountryRepository countryRepository;

    @BeforeEach
    void setup() {
        showRepository.deleteAll();
        genreRepository.deleteAll();
        countryRepository.deleteAll();

        Genre action = genreRepository.save(Genre.builder().name("Action").build());
        Genre drama = genreRepository.save(Genre.builder().name("Drama").build());
        Country usa = countryRepository.save(Country.builder().name("USA").build());
        Country uk = countryRepository.save(Country.builder().name("UK").build());

        LocalDate date2023 = LocalDate.of(2023, 1, 1);
        LocalDate date2024 = LocalDate.of(2024, 1, 1);

        Show show1 = Show.builder()
                .title("Breaking Action")
                .originalTitle("Breaking Action")
                .firstAirDate(date2023)
                .lastAirDate(date2023)
                .imageUrl("https://image1.png")
                .status(ShowStatus.ONGOING)
                .genres(Set.of(action))
                .countries(Set.of(usa))
                .build();

        Show show2 = Show.builder()
                .title("Drama Queen")
                .originalTitle("Drama Queen")
                .firstAirDate(date2024)
                .lastAirDate(date2024)
                .imageUrl("https://image2.png")
                .status(ShowStatus.ENDED)
                .genres(Set.of(drama))
                .countries(Set.of(uk))
                .build();

        Show show3 = Show.builder()
                .title("Action in USA")
                .originalTitle("Action USA")
                .firstAirDate(date2023)
                .lastAirDate(date2023)
                .imageUrl("https://image3.png")
                .status(ShowStatus.ONGOING)
                .genres(Set.of(action, drama))
                .countries(Set.of(usa, uk))
                .build();

        showRepository.saveAll(Set.of(show1, show2, show3));
    }

    @Test
    void searchShows_bySearchQuery_shouldFilterByTitleCaseInsensitive() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("q", "action"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[*].title", hasItems("Breaking Action", "Action in USA")));
    }

    @Test
    void searchShows_byGenre_shouldFilterCorrectly() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("genre", "Drama"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[*].title", hasItems("Drama Queen", "Action in USA")));
    }

    @Test
    void searchShows_byCountry_shouldFilterCorrectly() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("country", "USA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[*].title", hasItems("Breaking Action", "Action in USA")));
    }

    @Test
    void searchShows_byStatus_shouldFilterCorrectly() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("status", "ENDED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Drama Queen"));
    }

    @Test
    void searchShows_byYear_shouldFilterCorrectly() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("year", "2023"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[*].title", hasItems("Breaking Action", "Action in USA")));
    }

    @Test
    void searchShows_combinedFilters_shouldApplyAllCorrectly() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("q", "action")
                        .param("genre", "Action")
                        .param("country", "USA")
                        .param("status", "ONGOING")
                        .param("year", "2023"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[*].title", hasItems("Breaking Action", "Action in USA")));
    }

    @Test
    void searchShows_noResults_shouldReturnEmptyContent() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("q", "nonexistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(0));
    }

    @Test
    void searchShows_blankGenre_shouldIgnoreFilter() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("genre", " "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3));

    }

    @Test
    void searchShows_blankSearch_shouldIgnoreFilter() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("q", " "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3));
    }

    @Test
    void searchShows_blankCountry_shouldIgnoreFilter() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("country", " "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3));
    }

    @Test
    void searchShows_blankStatus_shouldIgnoreFilter() throws Exception {
        mockMvc.perform(get("/api/shows")
                        .param("status", " "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3));
    }
}
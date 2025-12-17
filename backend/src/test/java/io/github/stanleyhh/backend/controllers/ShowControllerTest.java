package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.entities.Country;
import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.domain.entities.Season;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.User;
import io.github.stanleyhh.backend.domain.entities.UserShow;
import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import io.github.stanleyhh.backend.repositories.CountryRepository;
import io.github.stanleyhh.backend.repositories.EpisodeRepository;
import io.github.stanleyhh.backend.repositories.GenreRepository;
import io.github.stanleyhh.backend.repositories.SeasonRepository;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import io.github.stanleyhh.backend.repositories.UserRepository;
import io.github.stanleyhh.backend.repositories.UserShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class ShowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserShowRepository userShowRepository;


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

    @Test
    void getShowDetails_shouldReturn200AndCorrectBody() throws Exception {
        Show show = showRepository.save(
                Show.builder()
                        .title("Terminator")
                        .originalTitle("Terminator")
                        .status(ShowStatus.ONGOING)
                        .firstAirDate(LocalDate.of(2020, 1, 1))
                        .build()
        );

        Season season = seasonRepository.save(
                Season.builder()
                        .number(1L)
                        .show(show)
                        .build()
        );

        episodeRepository.save(
                Episode.builder()
                        .number(1L)
                        .title("Ep1")
                        .releaseDate(LocalDate.now())
                        .runtime(40)
                        .season(season)
                        .build()
        );

        episodeRepository.save(
                Episode.builder()
                        .number(2L)
                        .title("Ep2")
                        .releaseDate(LocalDate.now())
                        .runtime(60)
                        .season(season)
                        .build()
        );

        User user = userRepository.save(User.builder().build());

        userShowRepository.save(
                UserShow.builder()
                        .user(user)
                        .show(show)
                        .rating(8)
                        .status(UserShowStatus.WATCHING)
                        .build()
        );

        mockMvc.perform(get("/api/shows/" + show.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(show.getId()))
                .andExpect(jsonPath("$.title").value("Terminator"))
                .andExpect(jsonPath("$.seasons").isArray())
                .andExpect(jsonPath("$.seasons.length()").value(1))
                .andExpect(jsonPath("$.seasons[0].number").value(1))
                .andExpect(jsonPath("$.seasons[0].episodes").isArray())
                .andExpect(jsonPath("$.seasons[0].episodes.length()").value(2))
                .andExpect(jsonPath("$.seasons[0].episodes[0].number").value(2))
                .andExpect(jsonPath("$.seasons[0].episodes[1].number").value(1))
                .andExpect(jsonPath("$.averageRating").value(8.0))
                .andExpect(jsonPath("$.watchedBy").value(1))
                .andExpect(jsonPath("$.totalRuntime").value(100))
                .andExpect(jsonPath("$.averageEpisodeRuntime").value(50));
    }

    @Test
    void getShowDetails_shouldReturn404_whenShowNotExists() throws Exception {
        mockMvc.perform(get("/api/shows/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getShowDetails_shouldReturn500_whenApiNotExists() throws Exception {
        mockMvc.perform(get("/1"))
                .andExpect(status().isInternalServerError());
    }
}
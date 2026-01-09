package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Season;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.User;
import io.github.stanleyhh.backend.domain.entities.UserEpisode;
import io.github.stanleyhh.backend.domain.entities.embeddable.UserEpisodeId;
import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import io.github.stanleyhh.backend.repositories.EpisodeRepository;
import io.github.stanleyhh.backend.repositories.SeasonRepository;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import io.github.stanleyhh.backend.repositories.UserEpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserEpisodeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserEpisodeRepository userEpisodeRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private ShowRepository showRepository;

    private User user;
    private Episode episode;

    @BeforeEach
    void setup() {
        user = userRepository.save(User.builder()
                .name("testUser")
                .build());

        Show show = Show.builder()
                .title("Terminator")
                .originalTitle("Terminator")
                .status(ShowStatus.ONGOING)
                .firstAirDate(LocalDate.now())
                .build();
        show = showRepository.save(show);

        Season season = Season.builder()
                .number(2L)
                .show(show)
                .build();
        season = seasonRepository.save(season);

        episode = Episode.builder()
                .season(season)
                .image("episodeImage")
                .number(2L)
                .releaseDate(LocalDate.of(2022, 4, 5))
                .runtime(25)
                .title("episodeTitle")
                .build();
        episode = episodeRepository.save(episode);
    }

    @Test
    void addMyEpisodes_shouldCreateNewUserEpisode() throws Exception {


        mockMvc.perform(post("/api/me/episodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2]")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                        ))
                )
                .andExpect(status().isNoContent());
        var userEpisode = userEpisodeRepository.findByEpisodeAndUser(episode, user);
        assertTrue(userEpisode.isPresent());
    }

    @Test
    void deleteMyEpisodes_shouldDeleteUserEpisode() throws Exception {
        mockMvc.perform(delete("/api/me/episodes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2]")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                        ))
                )
                .andExpect(status().isNoContent());
        var userEpisode = userEpisodeRepository.findByEpisodeAndUser(episode, user);
        assertFalse(userEpisode.isPresent());
    }

    @Test
    void updateMyEpisodeRating_shouldUpdateExistingRating() throws Exception {
        userEpisodeRepository.save(
                UserEpisode.builder()
                        .user(user)
                        .episode(episode)
                        .rating(5)
                        .id(new UserEpisodeId(user.getId(), episode.getId()))
                        .build()
        );

        mockMvc.perform(patch("/api/me/episodes/{episode_id}/rating",
                        episode.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("3")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", user.getName())
                        )))
                .andExpect(status().isNoContent());

        var userEpisode = userEpisodeRepository.findByEpisodeAndUser(episode, user);
        assertTrue(userEpisode.isPresent());
        assertEquals(3, userEpisode.get().getRating());
    }

    @Test
    void updateMyEpisodeRating_shouldAddNotExistingRating() throws Exception {
        mockMvc.perform(patch("/api/me/episodes/{episode_id}/rating",
                        episode.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("3")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", user.getName())
                        )))
                .andExpect(status().isNoContent());

        var userEpisode = userEpisodeRepository.findByEpisodeAndUser(episode, user);
        assertTrue(userEpisode.isPresent());
        assertEquals(3, userEpisode.get().getRating());
    }
}
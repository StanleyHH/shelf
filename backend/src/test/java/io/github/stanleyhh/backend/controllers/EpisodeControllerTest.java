package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Season;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.User;
import io.github.stanleyhh.backend.domain.entities.UserEpisode;
import io.github.stanleyhh.backend.domain.entities.UserShow;
import io.github.stanleyhh.backend.domain.entities.embeddable.UserEpisodeId;
import io.github.stanleyhh.backend.domain.entities.embeddable.UserShowId;
import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import io.github.stanleyhh.backend.repositories.EpisodeRepository;
import io.github.stanleyhh.backend.repositories.SeasonRepository;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import io.github.stanleyhh.backend.repositories.UserEpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserRepository;
import io.github.stanleyhh.backend.repositories.UserShowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EpisodeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private UserShowRepository userShowRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private UserEpisodeRepository userEpisodeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void getEpisodeDetails_shouldReturn200AndCorrectBody_whenIdExists() throws Exception {
        Show show = Show.builder()
                .title("Terminator")
                .originalTitle("Terminator")
                .status(ShowStatus.ONGOING)
                .firstAirDate(LocalDate.now())
                .build();
        show = showRepository.save(show);

        User user1 = userRepository.save(new User());
        User user2 = userRepository.save(new User());
        User user3 = userRepository.save(new User());
        User user4 = userRepository.save(new User());
        User user5 = userRepository.save(new User());
        User user6 = userRepository.save(new User());
        User user7 = userRepository.save(new User());

        UserShow userShow1 = UserShow.builder()
                .id(new UserShowId(user1.getId(), show.getId()))
                .status(UserShowStatus.WATCHING)
                .user(user1)
                .show(show)
                .build();
        UserShow userShow2 = UserShow.builder()
                .id(new UserShowId(user2.getId(), show.getId()))
                .status(UserShowStatus.NOT_WATCHING)
                .user(user2)
                .show(show)
                .build();
        UserShow userShow3 = UserShow.builder()
                .id(new UserShowId(user3.getId(), show.getId()))
                .status(UserShowStatus.DROPPED)
                .user(user3)
                .show(show)
                .build();
        UserShow userShow4 = UserShow.builder()
                .id(new UserShowId(user4.getId(), show.getId()))
                .status(UserShowStatus.WATCHING)
                .user(user4)
                .show(show)
                .build();
        UserShow userShow5 = UserShow.builder()
                .id(new UserShowId(user5.getId(), show.getId()))
                .status(UserShowStatus.WATCHING)
                .user(user5)
                .show(show)
                .build();
        UserShow userShow6 = UserShow.builder()
                .id(new UserShowId(user6.getId(), show.getId()))
                .status(UserShowStatus.WATCHING)
                .user(user6)
                .show(show)
                .build();
        UserShow userShow7 = UserShow.builder()
                .id(new UserShowId(user7.getId(), show.getId()))
                .status(UserShowStatus.WATCHING)
                .user(user7)
                .show(show)
                .build();
        userShowRepository.saveAll(List.of(userShow1, userShow2, userShow3, userShow4, userShow5, userShow6, userShow7));

        Season season = Season.builder()
                .number(2L)
                .show(show)
                .build();
        season = seasonRepository.save(season);

        Episode episode = Episode.builder()
                .season(season)
                .image("episodeImage")
                .number(2L)
                .releaseDate(LocalDate.of(2022, 4, 5))
                .runtime(25)
                .title("episodeTitle")
                .build();
        episode = episodeRepository.save(episode);

        UserEpisode userEpisode1 = UserEpisode.builder()
                .id(new UserEpisodeId(user1.getId(), episode.getId()))
                .watchedDate(LocalDate.now())
                .user(user1)
                .episode(episode)
                .build();
        UserEpisode userEpisode2 = UserEpisode.builder()
                .id(new UserEpisodeId(user2.getId(), episode.getId()))
                .watchedDate(LocalDate.now())
                .rating(5)
                .user(user2)
                .episode(episode)
                .build();
        UserEpisode userEpisode3 = UserEpisode.builder()
                .id(new UserEpisodeId(user3.getId(), episode.getId()))
                .rating(3)
                .user(user3)
                .episode(episode)
                .build();
        UserEpisode userEpisode4 = UserEpisode.builder()
                .id(new UserEpisodeId(user4.getId(), episode.getId()))
                .watchedDate(LocalDate.now())
                .rating(1)
                .user(user4)
                .episode(episode)
                .build();
        UserEpisode userEpisode5 = UserEpisode.builder()
                .id(new UserEpisodeId(user5.getId(), episode.getId()))
                .watchedDate(LocalDate.now())
                .rating(4)
                .user(user5)
                .episode(episode)
                .build();
        userEpisodeRepository.saveAll(List.of(userEpisode1, userEpisode2, userEpisode3, userEpisode4, userEpisode5));

        mockMvc.perform(get("/api/shows/1/episodes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(episode.getId()))
                .andExpect(jsonPath("$.episodeNumber").value(episode.getNumber()))
                .andExpect(jsonPath("$.title").value(episode.getTitle()))
                .andExpect(jsonPath("$.releaseDate").value("05.04.2022"))
                .andExpect(jsonPath("$.runtime").value(episode.getRuntime()))
                .andExpect(jsonPath("$.image").value(episode.getImage()))
                .andExpect(jsonPath("$.seasonNumber").value(season.getNumber()))
                .andExpect(jsonPath("$.averageRating").value(3.25))
                .andExpect(jsonPath("$.averageRatingVotesCount").value(4))
                .andExpect(jsonPath("$.watchedBy").value(4))
                .andExpect(jsonPath("$.watchedByPercent").value("80.00%"));
    }
}
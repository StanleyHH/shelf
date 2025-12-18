package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.dtos.EpisodeDetailsResponseDto;
import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Season;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.UserEpisode;
import io.github.stanleyhh.backend.domain.entities.UserShow;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import io.github.stanleyhh.backend.mappers.EpisodeMapper;
import io.github.stanleyhh.backend.repositories.EpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserEpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserShowRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EpisodeServiceImplTest {

    @Mock
    EpisodeRepository episodeRepository;

    @Mock
    UserEpisodeRepository userEpisodeRepository;

    @Mock
    UserShowRepository userShowRepository;

    @Spy
    EpisodeMapper episodeMapper = Mappers.getMapper(EpisodeMapper.class);

    @InjectMocks
    EpisodeServiceImpl episodeService;

    @Test
    void getEpisodeDetails_shouldReturnCorrectDto_whenIdExists() {
        Show show = Show.builder()
                .id(1L)
                .build();

        UserShow userShow1 = UserShow.builder()
                .status(UserShowStatus.WATCHING)
                .show(show)
                .build();
        UserShow userShow2 = UserShow.builder()
                .status(UserShowStatus.NOT_WATCHING)
                .show(show)
                .build();
        UserShow userShow3 = UserShow.builder()
                .status(UserShowStatus.DROPPED)
                .show(show)
                .build();
        UserShow userShow4 = UserShow.builder()
                .status(UserShowStatus.WATCHING)
                .show(show)
                .build();
        UserShow userShow5 = UserShow.builder()
                .status(UserShowStatus.WATCHING)
                .show(show)
                .build();
        UserShow userShow6 = UserShow.builder()
                .status(UserShowStatus.WATCHING)
                .show(show)
                .build();
        UserShow userShow7 = UserShow.builder()
                .status(UserShowStatus.WATCHING)
                .show(show)
                .build();

        Season season = Season.builder()
                .id(1L)
                .number(2L)
                .show(show)
                .build();

        Episode episode = Episode.builder()
                .id(1L)
                .season(season)
                .image("episodeImage")
                .number(2L)
                .releaseDate(LocalDate.now())
                .runtime(25)
                .title("episodeTitle")
                .build();

        UserEpisode userEpisode1 = UserEpisode.builder()
                .watchedDate(LocalDate.now())
                .episode(episode)
                .build();
        UserEpisode userEpisode2 = UserEpisode.builder()
                .watchedDate(LocalDate.now())
                .rating(5)
                .episode(episode)
                .build();
        UserEpisode userEpisode3 = UserEpisode.builder()
                .rating(3)
                .episode(episode)
                .build();
        UserEpisode userEpisode4 = UserEpisode.builder()
                .watchedDate(LocalDate.now())
                .rating(1)
                .episode(episode)
                .build();
        UserEpisode userEpisode5 = UserEpisode.builder()
                .watchedDate(LocalDate.now())
                .rating(4)
                .episode(episode)
                .build();

        when(episodeRepository.findById(episode.getId())).thenReturn(Optional.of(episode));
        when(userEpisodeRepository.findAllByEpisode(episode))
                .thenReturn(List.of(userEpisode1, userEpisode2, userEpisode3, userEpisode4, userEpisode5));
        when(userShowRepository.findAllByShow(show))
                .thenReturn(List.of(userShow1, userShow2, userShow3, userShow4, userShow5, userShow6, userShow7));

        EpisodeDetailsResponseDto result = episodeService.getEpisodeDetails(show.getId(), episode.getId());

        assertEquals(episode.getId(), result.getId());
        assertEquals(episode.getNumber(), result.getEpisodeNumber());
        assertEquals(episode.getTitle(), result.getTitle());
        assertEquals(episode.getReleaseDate(), result.getReleaseDate());
        assertEquals(episode.getRuntime(), result.getRuntime());
        assertEquals(episode.getImage(), result.getImage());
        assertEquals(episode.getSeason().getNumber(), result.getSeasonNumber());
        assertEquals(3.25, result.getAverageRating());
        assertEquals(4, result.getAverageRatingVotesCount());
        assertEquals(4, result.getWatchedBy());
        assertEquals("80.00%", result.getWatchedByPercent());
    }

    @Test
    void getEpisodeDetails__shouldThrowException_whenEpisodeIdNotExists() {
        when(episodeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> episodeService.getEpisodeDetails(1L, 1L)
        );

        assertEquals("Episode not found with id: 1", exception.getMessage());
    }

    @Test
    void getEpisodeDetails__shouldThrowException_whenEpisodeIdNotFoundForShowId() {
        Show show = Show.builder().id(1L).build();
        Season season = Season.builder().id(1L).show(show).build();
        Episode episode = Episode.builder().id(1L).season(season).build();

        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> episodeService.getEpisodeDetails(2L, 1L)
        );

        assertEquals("Episode with id 1 not found for show with id 2", exception.getMessage());
    }
}
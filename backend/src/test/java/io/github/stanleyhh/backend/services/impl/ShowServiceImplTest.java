package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.dtos.CountryDto;
import io.github.stanleyhh.backend.domain.dtos.EpisodeDto;
import io.github.stanleyhh.backend.domain.dtos.GenreDto;
import io.github.stanleyhh.backend.domain.dtos.SeasonDto;
import io.github.stanleyhh.backend.domain.dtos.ShowDetailsResponseDto;
import io.github.stanleyhh.backend.domain.entities.Country;
import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.domain.entities.Season;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.UserShow;
import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import io.github.stanleyhh.backend.mappers.CountryMapper;
import io.github.stanleyhh.backend.mappers.EpisodeMapper;
import io.github.stanleyhh.backend.mappers.GenreMapper;
import io.github.stanleyhh.backend.mappers.SeasonMapper;
import io.github.stanleyhh.backend.mappers.ShowActorMapper;
import io.github.stanleyhh.backend.mappers.ShowMapper;
import io.github.stanleyhh.backend.repositories.EpisodeRepository;
import io.github.stanleyhh.backend.repositories.SeasonRepository;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import io.github.stanleyhh.backend.repositories.UserRepository;
import io.github.stanleyhh.backend.repositories.UserShowRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowServiceImplTest {

    @Mock
    private ShowRepository showRepository;
    @Mock
    private SeasonRepository seasonRepository;
    @Mock
    private EpisodeRepository episodeRepository;
    @Mock
    private UserShowRepository userShowRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ShowMapper showMapper;
    @Mock
    private SeasonMapper seasonMapper;
    @Mock
    private EpisodeMapper episodeMapper;
    @Mock
    private CountryMapper countryMapper;
    @Mock
    private GenreMapper genreMapper;
    @Mock
    private ShowActorMapper showActorMapper;

    @InjectMocks
    private ShowServiceImpl showService;

    @Test
    void searchShows_shouldReturnPageFromRepository() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Show> expectedPage = new PageImpl<>(List.of(new Show()));
        when(showRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Show> result = showService.searchShows(null, pageable);

        verify(showRepository).findAll(pageable);
        assertEquals(expectedPage, result);
    }

    @Test
    void getShowDetails_shouldReturnCorrectDto_whenIdExists() {
        Long showId = 1L;
        Country country = Country.builder()
                .build();

        Genre genre = Genre.builder()
                .build();

        Show show = Show.builder()
                .id(showId)
                .title("Terminator")
                .status(ShowStatus.ONGOING)
                .countries(Set.of(country))
                .genres(Set.of(genre))
                .build();

        Season season = Season.builder()
                .id(10L)
                .number(1L)
                .show(show)
                .build();

        Episode episode1 = Episode.builder()
                .id(100L)
                .number(1L)
                .runtime(45)
                .season(season)
                .build();

        Episode episode2 = Episode.builder()
                .id(101L)
                .number(2L)
                .runtime(55)
                .season(season)
                .build();

        UserShow userShow1 = UserShow.builder()
                .rating(8)
                .status(UserShowStatus.WATCHING)
                .show(show)
                .build();

        UserShow userShow2 = UserShow.builder()
                .rating(6)
                .status(UserShowStatus.DROPPED)
                .show(show)
                .build();

        ShowDetailsResponseDto baseDto = ShowDetailsResponseDto.builder()
                .id(showId)
                .title("Terminator")
                .build();

        SeasonDto seasonDto = SeasonDto.builder().build();
        EpisodeDto episodeDto1 = EpisodeDto.builder().build();
        EpisodeDto episodeDto2 = EpisodeDto.builder().build();


        when(showRepository.findById(showId)).thenReturn(Optional.of(show));
        when(showMapper.toBaseDetailsDto(show)).thenReturn(baseDto);

        when(countryMapper.toDto(country)).thenReturn(CountryDto.builder().build());
        when(genreMapper.toDto(genre)).thenReturn(GenreDto.builder().build());

        when(seasonRepository.findAllByShowOrderByNumberDesc(show))
                .thenReturn(List.of(season));

        when(seasonMapper.toDto(season)).thenReturn(seasonDto);

        when(episodeRepository.findAllBySeasonOrderByNumberDesc(season))
                .thenReturn(List.of(episode1, episode2));

        when(episodeRepository.findAllBySeasonId(season.getId()))
                .thenReturn(List.of(episode1, episode2));

        when(episodeMapper.toDto(episode1)).thenReturn(episodeDto1);
        when(episodeMapper.toDto(episode2)).thenReturn(episodeDto2);

        when(userShowRepository.findAllByShow(show))
                .thenReturn(List.of(userShow1, userShow2));

        when(userShowRepository.findAllByShowAndStatus(show, UserShowStatus.WATCHING))
                .thenReturn(List.of(userShow1));

        when(userRepository.count()).thenReturn(100L);

        ShowDetailsResponseDto result = showService.getShowDetails(showId);

        assertNotNull(result);
        assertEquals(showId, result.getId());
        assertEquals("Terminator", result.getTitle());

        assertEquals(2, result.getAverageRatingVotesCount());
        assertEquals(7.0, result.getAverageRating()); // (8 + 6) / 2

        assertEquals(1, result.getWatchedBy());
        assertEquals(100, result.getUsersTotal());

        assertEquals(100, result.getTotalRuntime()); // 45 + 55
        assertEquals(50, result.getAverageEpisodeRuntime());

        assertEquals(1, result.getSeasons().size());
        assertEquals(2, result.getSeasons().getFirst().getEpisodes().size());

        verify(showRepository).findById(showId);
    }

    @Test
    void getShowDetails_shouldThrowException_whenIdNotExists() {
        when(showRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> showService.getShowDetails(1L)
        );

        assertEquals("Show not found with id: 1", exception.getMessage());
    }
}
package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.dtos.ActorRoleDto;
import io.github.stanleyhh.backend.domain.dtos.CountryDto;
import io.github.stanleyhh.backend.domain.dtos.EpisodeDto;
import io.github.stanleyhh.backend.domain.dtos.GenreDto;
import io.github.stanleyhh.backend.domain.dtos.SeasonDto;
import io.github.stanleyhh.backend.domain.dtos.ShowDetailsResponseDto;
import io.github.stanleyhh.backend.domain.dtos.ShowQueryParams;
import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Season;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.UserShow;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import io.github.stanleyhh.backend.domain.specifications.ShowSpecs;
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
import io.github.stanleyhh.backend.services.ShowService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.IntSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;
    private final SeasonRepository seasonRepository;
    private final ShowMapper showMapper;
    private final SeasonMapper seasonMapper;
    private final EpisodeRepository episodeRepository;
    private final EpisodeMapper episodeMapper;
    private final CountryMapper countryMapper;
    private final GenreMapper genreMapper;
    private final ShowActorMapper showActorMapper;
    private final UserShowRepository userShowRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Show> searchShows(ShowQueryParams params, Pageable pageable) {

        if (params == null) {
            return showRepository.findAll(pageable);
        }

        Specification<Show> spec = Specification.unrestricted();

        if (params.getSearch() != null && !params.getSearch().isBlank()) {
            spec = spec.and(ShowSpecs.containsText(params.getSearch()));
        }

        if (params.getGenreName() != null && !params.getGenreName().isBlank()) {
            spec = spec.and(ShowSpecs.hasGenre(params.getGenreName()));
        }

        if (params.getCountryName() != null && !params.getCountryName().isBlank()) {
            spec = spec.and(ShowSpecs.hasCountry(params.getCountryName()));
        }

        if (params.getStatus() != null && !params.getStatus().isBlank()) {
            spec = spec.and(ShowSpecs.hasStatus(params.getStatus()));
        }

        if (params.getYear() != null) {
            spec = spec.and(ShowSpecs.startedInYear(params.getYear()));
        }

        return showRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public ShowDetailsResponseDto getShowDetails(Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Show not found with id: " + id));
        ShowDetailsResponseDto responseDto = showMapper.toBaseDetailsDto(show);

        List<Season> seasons = seasonRepository.findAllByShowOrderByNumberDesc(show);
        List<SeasonDto> seasonDtos = seasons.stream().map(season -> {
            SeasonDto seasonDto = seasonMapper.toDto(season);
            List<Episode> episodes = episodeRepository.findAllBySeasonOrderByNumberDesc(season);
            List<EpisodeDto> episodeDtos = episodes.stream().map(episodeMapper::toDto).toList();
            seasonDto.setEpisodes(episodeDtos);
            return seasonDto;
        }).toList();
        responseDto.setSeasons(seasonDtos);

        List<CountryDto> countryDtos = show.getCountries().stream().map(countryMapper::toDto).toList();
        responseDto.setCountries(countryDtos);

        List<GenreDto> genreDtos = show.getGenres().stream().map(genreMapper::toDto).toList();
        responseDto.setGenres(genreDtos);

        List<ActorRoleDto> showActors = show.getShowActors().stream().map(showActorMapper::toActorRoleDto).toList();
        responseDto.setActors(showActors);

        List<UserShow> userShows = userShowRepository.findAllByShow(show);
        double averageRating = userShows.stream().mapToInt(UserShow::getRating).average().orElse(0);
        responseDto.setAverageRating(averageRating);
        responseDto.setAverageRatingVotesCount(userShows.size());

        long usersTotal = userRepository.count();
        responseDto.setUsersTotal((int) usersTotal);

        List<UserShow> userShowsWatching = userShowRepository.findAllByShowAndStatus(show, UserShowStatus.WATCHING);
        responseDto.setWatchedBy(userShowsWatching.size());

        IntSummaryStatistics stats = seasons.stream()
                .flatMap(season ->
                        episodeRepository.findAllBySeasonId(season.getId()).stream()
                )
                .mapToInt(Episode::getRuntime)
                .summaryStatistics();

        int totalRuntime = (int) stats.getSum();
        int averageRuntime = (int) stats.getAverage();

        responseDto.setTotalRuntime(totalRuntime);
        responseDto.setAverageEpisodeRuntime(averageRuntime);


        return responseDto;
    }
}


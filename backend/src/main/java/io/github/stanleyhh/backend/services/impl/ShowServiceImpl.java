package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.dtos.ActorRoleDto;
import io.github.stanleyhh.backend.domain.dtos.CountryDto;
import io.github.stanleyhh.backend.domain.dtos.EpisodeDto;
import io.github.stanleyhh.backend.domain.dtos.GenreDto;
import io.github.stanleyhh.backend.domain.dtos.SeasonDto;
import io.github.stanleyhh.backend.domain.dtos.ShowDetailsResponseDto;
import io.github.stanleyhh.backend.domain.dtos.ShowQueryParams;
import io.github.stanleyhh.backend.domain.dtos.UserShowDto;
import io.github.stanleyhh.backend.domain.dtos.UserShowEpisodeDto;
import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Season;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.User;
import io.github.stanleyhh.backend.domain.entities.UserShow;
import io.github.stanleyhh.backend.domain.entities.embeddable.UserShowId;
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
import io.github.stanleyhh.backend.repositories.UserEpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserRepository;
import io.github.stanleyhh.backend.repositories.UserShowRepository;
import io.github.stanleyhh.backend.services.ShowService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final UserEpisodeRepository userEpisodeRepository;

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
    public ShowDetailsResponseDto getShowDetails(Long id, OAuth2User oAuth2User) {
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

        IntSummaryStatistics ratingStats = userShows.stream()
                .mapToInt(UserShow::getRating)
                .filter(rating -> rating != 0)
                .summaryStatistics();

        responseDto.setAverageRating(ratingStats.getCount() == 0 ? 0 : ratingStats.getAverage());
        responseDto.setAverageRatingVotesCount((int) ratingStats.getCount());

        long usersTotal = userRepository.count();
        responseDto.setUsersTotal((int) usersTotal);

        List<UserShow> userShowsWatching = userShowRepository.findAllByShowAndStatus(show, UserShowStatus.WATCHING);
        responseDto.setWatchedBy(userShowsWatching.size());

        IntSummaryStatistics runtimeStats = seasons.stream()
                .flatMap(season ->
                        episodeRepository.findAllBySeasonId(season.getId()).stream()
                )
                .mapToInt(Episode::getRuntime)
                .summaryStatistics();

        int totalRuntime = (int) runtimeStats.getSum();
        int averageRuntime = (int) runtimeStats.getAverage();

        responseDto.setTotalRuntime(totalRuntime);
        responseDto.setAverageEpisodeRuntime(averageRuntime);

        if (oAuth2User != null) {
            userRepository.findByName(oAuth2User.getAttribute("login"))
                    .ifPresent(user -> {
                        Optional<UserShow> userShow = userShowRepository
                                .findByShowAndUser(show, user);
                        UserShowStatus status = userShow
                                .map(UserShow::getStatus)
                                .orElse(UserShowStatus.NOT_WATCHING);
                        Integer rating = userShow
                                .map(UserShow::getRating)
                                .orElse(0);
                        Set<UserShowEpisodeDto> watchedEpisodes = userEpisodeRepository.findAllByUser(user).stream()
                                .filter(userEpisode -> Objects.equals(userEpisode
                                        .getEpisode()
                                        .getSeason()
                                        .getShow()
                                        .getId(), id))
                                .map(userEpisode -> UserShowEpisodeDto.builder()
                                        .id(userEpisode.getEpisode().getId())
                                        .rating(userEpisode.getRating())
                                        .build())
                                .collect(Collectors.toSet());
                        responseDto.setUserData(
                                UserShowDto.builder()
                                        .status(status)
                                        .rating(rating)
                                        .watchedEpisodes(watchedEpisodes)
                                        .build()
                        );
                    });
        }
        return responseDto;
    }

    @Override
    @Transactional
    public void updateUserShowStatus(Long showId, UserShowStatus status, OAuth2User oAuth2User) {
        if (oAuth2User != null) {
            Show show = showRepository.findById(showId)
                    .orElseThrow(() -> new EntityNotFoundException("Show not found with id: " + showId));
            String userName = oAuth2User.getAttribute("login");
            User user = userRepository.findByName(userName)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + userName));
            Optional<UserShow> userShow = userShowRepository.findByShowAndUser(show, user);
            if (status == UserShowStatus.NOT_WATCHING) {
                userShow.ifPresent(userShowRepository::delete);
            } else {
                UserShow userShowToSave = UserShow.builder()
                        .user(user)
                        .show(show)
                        .status(status)
                        .rating(0)
                        .build();
                if (userShow.isPresent()) {
                    UserShowId userShowId = new UserShowId(user.getId(), show.getId());
                    userShowToSave.setId(userShowId);
                    userShowToSave.setRating(userShow.get().getRating());
                }
                userShowRepository.save(userShowToSave);
            }
        }
    }

    @Override
    @Transactional
    public void updateUserShowRating(Long showId, Integer rating, OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new IllegalArgumentException("OAuth2User is null");
        }

        String userName = oAuth2User.getAttribute("login");

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new EntityNotFoundException("Show not found with id: " + showId));

        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + userName));

        UserShow userShow = userShowRepository
                .findByShowAndUser(show, user)
                .orElseGet(() -> UserShow.builder()
                        .user(user)
                        .show(show)
                        .status(UserShowStatus.WATCHING)
                        .build());

        userShow.setRating(rating);
        userShowRepository.save(userShow);
    }

}
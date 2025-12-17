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
import io.github.stanleyhh.backend.services.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    @Override
    public ShowDetailsResponseDto getShowDetails(Long id) {
        Show show = showRepository.findById(id).orElseThrow(RuntimeException::new);
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

        List<ActorRoleDto> showActors = show.getShowActors().stream().map(showActorMapper::toDto).toList();
        responseDto.setActors(showActors);

        return responseDto;
    }
}

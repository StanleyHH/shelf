package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.dtos.EpisodeDetailsResponseDto;
import io.github.stanleyhh.backend.domain.dtos.ShowTitleDto;
import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.UserEpisode;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import io.github.stanleyhh.backend.mappers.EpisodeMapper;
import io.github.stanleyhh.backend.repositories.EpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserEpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserShowRepository;
import io.github.stanleyhh.backend.services.EpisodeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {
    private final EpisodeRepository episodeRepository;
    private final EpisodeMapper episodeMapper;
    private final UserEpisodeRepository userEpisodeRepository;
    private final UserShowRepository userShowRepository;

    @Override
    public EpisodeDetailsResponseDto getEpisodeDetails(Long showId, Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found with id: " + episodeId));
        Show show = episode.getSeason().getShow();

        if (!Objects.equals(showId, show.getId())) {
            throw new EntityNotFoundException("Episode with id " + episodeId + " not found for show with id " + showId);
        }

        EpisodeDetailsResponseDto responseDto = episodeMapper.toBaseDetailsDto(episode);

        responseDto.setSeasonNumber(episode.getSeason().getNumber());

        List<UserEpisode> userEpisodes = userEpisodeRepository.findAllByEpisode(episode);
        IntSummaryStatistics ratingsStatistics = userEpisodes.stream()
                .filter(userEpisode -> userEpisode.getRating() != null)
                .mapToInt(UserEpisode::getRating)
                .summaryStatistics();

        responseDto.setAverageRating(ratingsStatistics.getAverage());
        responseDto.setAverageRatingVotesCount((int) ratingsStatistics.getCount());

        long episodeWatchedBy = userEpisodes.stream()
                .filter(userEpisode -> userEpisode.getWatchedDate() != null)
                .count();
        responseDto.setWatchedBy((int) episodeWatchedBy);

        long showWatchedBy = userShowRepository.findAllByShow(show).stream()
                .filter(userShow -> userShow.getStatus() == UserShowStatus.WATCHING)
                .count();

        String watchedByPercent = String.format(Locale.US, "%.2f%%", (episodeWatchedBy * 100.0) / showWatchedBy);
        responseDto.setWatchedByPercent(watchedByPercent);

        responseDto.setShow(ShowTitleDto.builder().id(show.getId()).title(show.getTitle()).build());

        return responseDto;
    }
}
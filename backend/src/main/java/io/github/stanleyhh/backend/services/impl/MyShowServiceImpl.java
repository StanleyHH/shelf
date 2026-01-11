package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.dtos.MyShowWatchingDto;
import io.github.stanleyhh.backend.domain.dtos.MyShowsDto;
import io.github.stanleyhh.backend.domain.dtos.MyShowsPlanToWatchDto;
import io.github.stanleyhh.backend.domain.dtos.SeasonDto;
import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Season;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.User;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import io.github.stanleyhh.backend.mappers.EpisodeMapper;
import io.github.stanleyhh.backend.mappers.SeasonMapper;
import io.github.stanleyhh.backend.repositories.EpisodeRepository;
import io.github.stanleyhh.backend.repositories.SeasonRepository;
import io.github.stanleyhh.backend.repositories.UserEpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserRepository;
import io.github.stanleyhh.backend.repositories.UserShowRepository;
import io.github.stanleyhh.backend.services.MyShowService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyShowServiceImpl implements MyShowService {
    private final UserRepository userRepository;
    private final UserShowRepository userShowRepository;
    private final SeasonRepository seasonRepository;
    private final SeasonMapper seasonMapper;
    private final EpisodeRepository episodeRepository;
    private final EpisodeMapper episodeMapper;
    private final UserEpisodeRepository userEpisodeRepository;

    @Override
    @Transactional
    public MyShowsDto getMyShows(OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new IllegalArgumentException("OAuth2User is null");
        }

        String userName = oAuth2User.getAttribute("login");
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + userName));

        List<MyShowWatchingDto> watching = new ArrayList<>();
        List<MyShowsPlanToWatchDto> planToWatch = new ArrayList<>();

        userShowRepository.findAllByUser(user).stream()
                .filter(userShow -> userShow.getStatus() == UserShowStatus.WATCHING
                        || userShow.getStatus() == UserShowStatus.PLAN_TO_WATCH)
                .forEach(userShow -> {
                    Show show = userShow.getShow();
                    List<Season> seasons = seasonRepository.findAllByShowOrderByNumberDesc(show);

                    if (userShow.getStatus() == UserShowStatus.WATCHING) {
                        List<SeasonDto> seasonDtos = new ArrayList<>();
                        int totalTime = 0;

                        for (Season season : seasons) {
                            List<Episode> episodes = episodeRepository
                                    .findAllBySeasonOrderByNumberDesc(season)
                                    .stream()
                                    .filter(episode -> userEpisodeRepository.findByEpisodeAndUser(episode, user).isEmpty())
                                    .toList();

                            if (episodes.isEmpty()) {
                                continue;
                            }

                            totalTime += episodes.stream().mapToInt(Episode::getRuntime).sum();

                            SeasonDto seasonDto = seasonMapper.toDto(season);
                            seasonDto.setEpisodes(episodes.stream().map(episodeMapper::toDto).toList());

                            seasonDtos.add(seasonDto);
                        }

                        watching.add(MyShowWatchingDto.builder()
                                .id(show.getId())
                                .title(show.getTitle())
                                .status(show.getStatus())
                                .totalTime(totalTime)
                                .seasons(seasonDtos)
                                .build());
                    } else {
                        planToWatch.add(MyShowsPlanToWatchDto.builder()
                                .id(show.getId())
                                .title(show.getTitle())
                                .status(show.getStatus())
                                .totalSeasons(seasons.size())
                                .build());
                    }
                });

        return MyShowsDto.builder()
                .watching(watching)
                .planToWatch(planToWatch)
                .build();
    }

}

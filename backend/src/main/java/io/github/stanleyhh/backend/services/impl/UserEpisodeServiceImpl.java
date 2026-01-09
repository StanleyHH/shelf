package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.User;
import io.github.stanleyhh.backend.domain.entities.UserEpisode;
import io.github.stanleyhh.backend.domain.entities.embeddable.UserEpisodeId;
import io.github.stanleyhh.backend.repositories.EpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserEpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserRepository;
import io.github.stanleyhh.backend.services.UserEpisodeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEpisodeServiceImpl implements UserEpisodeService {
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;
    private final UserEpisodeRepository userEpisodeRepository;

    @Override
    @Transactional
    public void addMyEpisode(List<Long> episodeIds, OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new IllegalArgumentException("OAuth2User is null");
        }

        String userName = oAuth2User.getAttribute("login");
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + userName));

        List<Episode> episodes = episodeRepository.findAllById(episodeIds);

        Set<Long> existingEpisodeIds =
                userEpisodeRepository.findAllByUserAndEpisodeIn(user, episodes)
                        .stream()
                        .map(ue -> ue.getEpisode().getId())
                        .collect(Collectors.toSet());

        List<UserEpisode> toSave = episodes.stream()
                .filter(e -> !existingEpisodeIds.contains(e.getId()))
                .map(e -> UserEpisode.builder()
                        .id(new UserEpisodeId(user.getId(), e.getId()))
                        .user(user)
                        .episode(e)
                        .watchedDate(LocalDate.now())
                        .rating(0)
                        .build())
                .toList();

        userEpisodeRepository.saveAll(toSave);
    }

    @Override
    @Transactional
    public void deleteMyEpisode(List<Long> episodeIds, OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new IllegalArgumentException("OAuth2User is null");
        }

        String userName = oAuth2User.getAttribute("login");
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + userName));

        userEpisodeRepository.deleteAllByUserAndEpisodeIdIn(user, episodeIds);
    }

    @Override
    public void updateMyEpisodeRating(Long episodeId, Integer rating, OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new IllegalArgumentException("OAuth2User is null");
        }

        String userName = oAuth2User.getAttribute("login");

         Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new EntityNotFoundException("Episode not found with id: " + episodeId));

        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with name: " + userName));

        UserEpisode userEpisode = userEpisodeRepository
                .findByEpisodeAndUser(episode, user)
                .orElseGet(() -> UserEpisode.builder()
                        .user(user)
                        .episode(episode)
                        .watchedDate(LocalDate.now())
                        .build());

        userEpisode.setRating(rating);
        userEpisodeRepository.save(userEpisode);
    }
}

package io.github.stanleyhh.backend.services;

import io.github.stanleyhh.backend.domain.dtos.EpisodeDetailsResponseDto;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface EpisodeService {
    EpisodeDetailsResponseDto getEpisodeDetails(Long showId, Long episodeId, OAuth2User oAuth2User);
}

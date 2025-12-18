package io.github.stanleyhh.backend.services;

import io.github.stanleyhh.backend.domain.dtos.EpisodeDetailsResponseDto;

public interface EpisodeService {
    EpisodeDetailsResponseDto getEpisodeDetails(Long showId, Long episodeId);
}

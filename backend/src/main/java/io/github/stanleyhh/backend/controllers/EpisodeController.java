package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.dtos.EpisodeDetailsResponseDto;
import io.github.stanleyhh.backend.services.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/shows/{show_id}/episodes/")
public class EpisodeController {

    private final EpisodeService episodeService;

    @GetMapping(path = "/{episode_id}")
    ResponseEntity<EpisodeDetailsResponseDto> getEpisodeDetails(
            @PathVariable("show_id") Long showId,
            @PathVariable("episode_id") Long episodeId
    ) {
        return ResponseEntity.ok(episodeService.getEpisodeDetails(showId, episodeId));
    }
}

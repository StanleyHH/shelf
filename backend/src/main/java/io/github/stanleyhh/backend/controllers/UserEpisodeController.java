package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.services.UserEpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me/episodes")
public class UserEpisodeController {
    private final UserEpisodeService userEpisodeService;

    @PostMapping
    public ResponseEntity<Void> addMyEpisodes(
            @RequestBody List<Long> episodeIds,
            @AuthenticationPrincipal OAuth2User oAuth2User) {
        userEpisodeService.addMyEpisode(episodeIds, oAuth2User);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMyEpisodes(
            @RequestBody List<Long> episodeIds,
            @AuthenticationPrincipal OAuth2User oAuth2User) {
        userEpisodeService.deleteMyEpisode(episodeIds, oAuth2User);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{episode_id}/rating")
    public ResponseEntity<Void> updateMyEpisodeRating(
            @PathVariable("episode_id") Long episodeId,
            @RequestBody Integer rating,
            @AuthenticationPrincipal OAuth2User oAuth2User
    ) {
        userEpisodeService.updateMyEpisodeRating(episodeId, rating, oAuth2User);
        return ResponseEntity.noContent().build();
    }
}

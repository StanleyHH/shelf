package io.github.stanleyhh.backend.services;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;

public interface UserEpisodeService {
    void addMyEpisode(List<Long> episodeIds, OAuth2User oAuth2User);

    void deleteMyEpisode(List<Long> episodeIds, OAuth2User oAuth2User);
}

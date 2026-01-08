package io.github.stanleyhh.backend.services;

import io.github.stanleyhh.backend.domain.dtos.ShowDetailsResponseDto;
import io.github.stanleyhh.backend.domain.dtos.ShowQueryParams;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface ShowService {
    Page<Show> searchShows(ShowQueryParams params, Pageable pageable);

    ShowDetailsResponseDto getShowDetails(Long id, OAuth2User user);

    void updateUserShowStatus(Long showId, UserShowStatus status, OAuth2User oAuth2User);

    void updateUserShowRating(Long showId, Integer rating, OAuth2User oAuth2User);
}

package io.github.stanleyhh.backend.services;

import io.github.stanleyhh.backend.domain.dtos.ShowDetailsResponseDto;
import io.github.stanleyhh.backend.domain.dtos.ShowQueryParams;
import io.github.stanleyhh.backend.domain.entities.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface ShowService {
    Page<Show> searchShows(ShowQueryParams params, Pageable pageable);

    ShowDetailsResponseDto getShowDetails(Long id, OAuth2User user);
}

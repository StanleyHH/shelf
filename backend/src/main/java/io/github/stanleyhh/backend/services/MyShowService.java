package io.github.stanleyhh.backend.services;

import io.github.stanleyhh.backend.domain.dtos.MyShowsDto;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface MyShowService {
    MyShowsDto getMyShows(OAuth2User oAuth2User);
}

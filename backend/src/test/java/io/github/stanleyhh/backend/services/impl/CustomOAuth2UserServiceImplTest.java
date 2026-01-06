package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.entities.User;
import io.github.stanleyhh.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomOAuth2UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OAuth2UserRequest userRequest;

    private CustomOAuth2UserServiceImpl service;

    private OAuth2User oAuth2User;

    @BeforeEach
    void setUp() {
        oAuth2User = new DefaultOAuth2User(
                Set.of(),
                Map.of(
                        "login", "testuser",
                        "avatar_url", "http://avatar"
                ),
                "login"
        );

        service = new CustomOAuth2UserServiceImpl(userRepository) {
            @Override
            protected OAuth2User loadOAuthUser(OAuth2UserRequest userRequest) {
                return oAuth2User;
            }
        };
    }

    @Test
    void loadUser_userAlreadyExists_doesNotSave() {
        when(userRepository.findByName("testuser"))
                .thenReturn(Optional.of(new User()));

        service.loadUser(userRequest);

        verify(userRepository, never()).save(any());
    }

    @Test
    void loadUser_userDoesNotExist_savesUser() {
        when(userRepository.findByName("testuser"))
                .thenReturn(Optional.empty());

        service.loadUser(userRequest);

        verify(userRepository).save(any(User.class));
    }
}

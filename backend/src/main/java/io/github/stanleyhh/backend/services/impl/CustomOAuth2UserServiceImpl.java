package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.entities.User;
import io.github.stanleyhh.backend.repositories.UserRepository;
import io.github.stanleyhh.backend.services.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserServiceImpl extends DefaultOAuth2UserService implements CustomOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String name = oAuth2User.getAttribute("login"); // GitHub username

        if (userRepository.findByName(name).isEmpty()) {
            createAppUser(oAuth2User);
        }

        return oAuth2User;
    }

    private void createAppUser(OAuth2User oAuth2User) {
        User user = User.builder()
                .name(oAuth2User.getAttribute("login"))
                .avatar(oAuth2User.getAttribute("avatar_url"))
                .build();

        userRepository.save(user);
    }
}

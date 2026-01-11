package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.dtos.MyShowsDto;
import io.github.stanleyhh.backend.services.MyShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/me/my-shows")
public class MyShowController {

    private final MyShowService myShowsService;

    @GetMapping
    public ResponseEntity<MyShowsDto> getMyShows(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return ResponseEntity.ok(myShowsService.getMyShows(oAuth2User));
    }
}

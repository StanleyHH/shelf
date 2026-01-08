package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.dtos.PageResponse;
import io.github.stanleyhh.backend.domain.dtos.ShowDetailsResponseDto;
import io.github.stanleyhh.backend.domain.dtos.ShowListResponseDto;
import io.github.stanleyhh.backend.domain.dtos.ShowQueryParams;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import io.github.stanleyhh.backend.mappers.ShowMapper;
import io.github.stanleyhh.backend.services.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/shows")
@RequiredArgsConstructor
public class ShowController {
    private final ShowService showService;
    private final ShowMapper showMapper;

    @GetMapping
    public PageResponse<ShowListResponseDto> searchShows(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "24") int size
    ) {
        Page<Show> shows = showService.searchShows(
                ShowQueryParams.builder()
                        .search(q)
                        .genreName(genre)
                        .countryName(country)
                        .status(status)
                        .year(year)
                        .build(),
                PageRequest.of(page - 1, size)
        );

        return new PageResponse<>(
                shows.map(showMapper::toShowListResponseDto).getContent(),
                shows.getNumber() + 1,
                shows.getSize(),
                shows.getTotalElements(),
                shows.getTotalPages()
        );
    }

    @GetMapping(path = "/{show_id}")
    public ResponseEntity<ShowDetailsResponseDto> getShowDetails(
            @PathVariable("show_id") Long showId,
            @AuthenticationPrincipal OAuth2User oAuth2User) {
        return ResponseEntity.ok(showService.getShowDetails(showId, oAuth2User));
    }

    @PutMapping(path = "/{show_id}/status")
    public ResponseEntity<Void> updateUserShowStatus(
            @PathVariable("show_id") Long showId,
            @RequestBody UserShowStatus status,
            @AuthenticationPrincipal OAuth2User oAuth2User
    ) {
        showService.updateUserShowStatus(showId, status, oAuth2User);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/{show_id}/rating")
    public ResponseEntity<Void> updateUserShowRating(
            @PathVariable("show_id") Long showId,
            @RequestBody Integer rating,
            @AuthenticationPrincipal OAuth2User oAuth2User
    ) {
        showService.updateUserShowRating(showId, rating, oAuth2User);
        return ResponseEntity.noContent().build();
    }
}

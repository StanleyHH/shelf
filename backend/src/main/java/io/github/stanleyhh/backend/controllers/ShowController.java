package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.dtos.PageResponse;
import io.github.stanleyhh.backend.domain.dtos.ShowListResponseDto;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.mappers.ShowMapper;
import io.github.stanleyhh.backend.services.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
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
    public PageResponse<ShowListResponseDto> getAllShows(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<Show> shows = showService.getAllShows(PageRequest.of(page - 1, size));
        return new PageResponse<>(
                shows.map(showMapper::toShowListResponseDto).getContent(),
                shows.getNumber() + 1,
                shows.getSize(),
                shows.getTotalElements(),
                shows.getTotalPages()
        );
    }
}

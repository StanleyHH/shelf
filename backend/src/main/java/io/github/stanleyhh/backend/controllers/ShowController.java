package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.dtos.ShowListItemDto;
import io.github.stanleyhh.backend.mappers.ShowMapper;
import io.github.stanleyhh.backend.services.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/shows")
@RequiredArgsConstructor
public class ShowController {
    private final ShowService showService;
    private final ShowMapper showMapper;

    @GetMapping
    public ResponseEntity<List<ShowListItemDto>> getAllShows() {
        List<ShowListItemDto> showDtos = showService
                .getAllShows()
                .stream()
                .map(showMapper::toShowListItemDto)
                .toList();
        return ResponseEntity.ok(showDtos);
    }
}

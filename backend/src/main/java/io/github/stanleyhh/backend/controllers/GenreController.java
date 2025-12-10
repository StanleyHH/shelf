package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.dtos.GenreDto;
import io.github.stanleyhh.backend.mappers.GenreMapper;
import io.github.stanleyhh.backend.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/genres")
public class GenreController {

    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @GetMapping
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<GenreDto> genreDtos = genreService.getAllGenres()
                .stream()
                .map(genreMapper::toDto)
                .toList();

        return ResponseEntity.ok(genreDtos);
    }
}

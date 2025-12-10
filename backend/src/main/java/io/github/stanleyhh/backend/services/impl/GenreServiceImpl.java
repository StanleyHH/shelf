package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.repositories.GenreRepository;
import io.github.stanleyhh.backend.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}

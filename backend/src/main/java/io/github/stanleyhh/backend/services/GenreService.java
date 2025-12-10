package io.github.stanleyhh.backend.services;

import io.github.stanleyhh.backend.domain.entities.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();
}

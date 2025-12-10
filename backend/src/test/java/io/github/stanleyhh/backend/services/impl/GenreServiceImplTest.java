package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.repositories.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    GenreServiceImpl genreService;

    @Test
    void getAllGenres() {
        Genre action = Genre.builder().id(UUID.randomUUID()).name("action").build();
        Genre fantasy = Genre.builder().id(UUID.randomUUID()).name("fantasy").build();
        List<Genre> expected = List.of(action, fantasy);
        when(genreRepository.findAll()).thenReturn(expected);

        List<Genre> actual = genreService.getAllGenres();

        verify(genreRepository).findAll();
        assertEquals(expected, actual);
    }
}
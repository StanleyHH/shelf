package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowServiceImplTest {

    @Mock
    private ShowRepository showRepository;

    @InjectMocks
    private ShowServiceImpl showService;

    @Test
    void searchShows_shouldReturnPageFromRepository() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Show> expectedPage = new PageImpl<>(List.of(new Show()));
        when(showRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<Show> result = showService.searchShows(null, pageable);

        verify(showRepository).findAll(pageable);
        assertEquals(expectedPage, result);
    }
}
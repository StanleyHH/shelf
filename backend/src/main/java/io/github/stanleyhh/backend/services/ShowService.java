package io.github.stanleyhh.backend.services;

import io.github.stanleyhh.backend.domain.ShowQueryParams;
import io.github.stanleyhh.backend.domain.entities.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShowService {
    Page<Show> searchShows(ShowQueryParams params, Pageable pageable);
}

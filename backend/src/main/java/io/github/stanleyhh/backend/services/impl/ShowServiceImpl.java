package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import io.github.stanleyhh.backend.services.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;

    @Override
    public Page<Show> getAllShows(Pageable pageable) {
        return showRepository.findAll(pageable);
    }
}

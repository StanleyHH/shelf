package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import io.github.stanleyhh.backend.services.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;

    @Override
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }
}

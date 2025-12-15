package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.dtos.ShowQueryParams;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.specifications.ShowSpecs;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import io.github.stanleyhh.backend.services.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;


    @Override
    public Page<Show> searchShows(ShowQueryParams params, Pageable pageable) {

        if (params == null) {
            return showRepository.findAll(pageable);
        }

        Specification<Show> spec = Specification.unrestricted();

        if (params.getSearch() != null && !params.getSearch().isBlank()) {
            spec = spec.and(ShowSpecs.containsText(params.getSearch()));
        }

        if (params.getGenreName() != null && !params.getGenreName().isBlank()) {
            spec = spec.and(ShowSpecs.hasGenre(params.getGenreName()));
        }

        if (params.getCountryName() != null && !params.getCountryName().isBlank()) {
            spec = spec.and(ShowSpecs.hasCountry(params.getCountryName()));
        }

        if (params.getStatus() != null && !params.getStatus().isBlank()) {
            spec = spec.and(ShowSpecs.hasStatus(params.getStatus()));
        }

        if (params.getYear() != null) {
            spec = spec.and(ShowSpecs.startedInYear(params.getYear()));
        }

        return showRepository.findAll(spec, pageable);
    }
}

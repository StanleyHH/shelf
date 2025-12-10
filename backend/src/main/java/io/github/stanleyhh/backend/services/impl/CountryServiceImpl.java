package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.entities.Country;
import io.github.stanleyhh.backend.repositories.CountryRepository;
import io.github.stanleyhh.backend.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
}

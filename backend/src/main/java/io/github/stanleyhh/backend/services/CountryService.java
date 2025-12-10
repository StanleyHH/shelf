package io.github.stanleyhh.backend.services;


import io.github.stanleyhh.backend.domain.entities.Country;

import java.util.List;

public interface CountryService {
    List<Country> getAllCountries();
}

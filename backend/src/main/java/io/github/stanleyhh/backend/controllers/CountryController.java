package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.dtos.CountryDto;
import io.github.stanleyhh.backend.mappers.CountryMapper;
import io.github.stanleyhh.backend.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    private final CountryMapper countryMapper;

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        List<CountryDto> countriesDtos = countryService.getAllCountries()
                .stream()
                .map(countryMapper::toDto)
                .toList();
        return ResponseEntity.ok(countriesDtos);
    }
}

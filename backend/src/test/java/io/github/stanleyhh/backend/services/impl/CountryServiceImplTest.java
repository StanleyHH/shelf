package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.entities.Country;
import io.github.stanleyhh.backend.repositories.CountryRepository;
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
class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    @Test
    void getAllCountries_shouldReturnAllCountries() {
        Country country = Country.builder()
                .id(UUID.randomUUID())
                .name("Germany")
                .build();
        List<Country> expected = List.of(country);
        when(countryRepository.findAll()).thenReturn(expected);

        List<Country> actual = countryService.getAllCountries();

        verify(countryRepository).findAll();
        assertEquals(expected, actual);
    }
}
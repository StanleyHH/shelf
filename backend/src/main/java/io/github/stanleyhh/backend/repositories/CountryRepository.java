package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Optional<Country> getCountryByName(String name);
}

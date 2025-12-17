package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    boolean existsBy();
}

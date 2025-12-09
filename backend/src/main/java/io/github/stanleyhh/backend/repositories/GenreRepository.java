package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

    boolean existsBy();

    Optional<Genre> getGenreByName(String name);
}

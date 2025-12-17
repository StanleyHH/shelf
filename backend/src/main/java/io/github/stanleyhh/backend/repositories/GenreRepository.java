package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    boolean existsBy();

}

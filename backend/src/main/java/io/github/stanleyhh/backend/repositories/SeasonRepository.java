package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, Long> {
    boolean existsBy();
}

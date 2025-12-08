package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShowRepository extends JpaRepository<Show, UUID> {
    boolean existsBy();
}

package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    boolean existsBy();
}

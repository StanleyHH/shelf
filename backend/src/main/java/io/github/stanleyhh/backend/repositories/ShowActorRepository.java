package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.ShowActor;
import io.github.stanleyhh.backend.domain.entities.embeddable.ShowActorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowActorRepository extends JpaRepository<ShowActor, ShowActorId> {
    boolean existsBy();
}

package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.UserEpisode;
import io.github.stanleyhh.backend.domain.entities.embeddable.UserEpisodeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEpisodeRepository extends JpaRepository<UserEpisode, UserEpisodeId> {
    boolean existsBy();
}

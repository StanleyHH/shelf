package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    boolean existsBy();

    List<Episode> findAllBySeasonId(Long id);
}

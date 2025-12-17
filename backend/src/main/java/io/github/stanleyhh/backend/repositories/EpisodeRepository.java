package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    boolean existsBy();

    List<Episode> findAllBySeasonId(Long id);

    List<Episode> findAllBySeasonOrderByNumberDesc(Season season);
}

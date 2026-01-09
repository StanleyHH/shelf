package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.User;
import io.github.stanleyhh.backend.domain.entities.UserEpisode;
import io.github.stanleyhh.backend.domain.entities.embeddable.UserEpisodeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserEpisodeRepository extends JpaRepository<UserEpisode, UserEpisodeId> {
    boolean existsBy();

    List<UserEpisode> findAllByEpisode(Episode episode);

    List<UserEpisode> findAllByUserAndEpisodeIn(User user, Collection<Episode> episodes);

    List<UserEpisode> findAllByUser(User user);

    void deleteAllByUserAndEpisodeIdIn(User user, List<Long> episodeIds);

    Optional<UserEpisode> findByEpisodeAndUser(Episode episode, User user);
}

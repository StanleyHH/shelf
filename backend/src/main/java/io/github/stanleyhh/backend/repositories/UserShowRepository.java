package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.UserShow;
import io.github.stanleyhh.backend.domain.entities.embeddable.UserShowId;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserShowRepository extends JpaRepository<UserShow, UserShowId> {
    boolean existsBy();

    List<UserShow> findAllByShow(Show show);

    List<UserShow> findAllByShowAndStatus(Show show, UserShowStatus status);
}

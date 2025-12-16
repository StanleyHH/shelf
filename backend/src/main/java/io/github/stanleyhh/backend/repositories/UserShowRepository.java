package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.UserShow;
import io.github.stanleyhh.backend.domain.entities.embeddable.UserShowId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserShowRepository extends JpaRepository<UserShow, UserShowId> {
    boolean existsBy();

}

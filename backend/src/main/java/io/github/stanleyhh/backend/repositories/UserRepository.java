package io.github.stanleyhh.backend.repositories;

import io.github.stanleyhh.backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsBy();

    Optional<User> findByName(String name);
}

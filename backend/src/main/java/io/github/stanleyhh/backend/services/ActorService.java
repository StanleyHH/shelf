package io.github.stanleyhh.backend.services;

import io.github.stanleyhh.backend.domain.dtos.ActorDetailsResponseDto;

public interface ActorService {
    ActorDetailsResponseDto getActorDetails(Long id);
}

package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.dtos.ActorDetailsResponseDto;
import io.github.stanleyhh.backend.domain.dtos.ShowActorDto;
import io.github.stanleyhh.backend.domain.entities.Actor;
import io.github.stanleyhh.backend.mappers.ActorMapper;
import io.github.stanleyhh.backend.mappers.ShowActorMapper;
import io.github.stanleyhh.backend.repositories.ActorRepository;
import io.github.stanleyhh.backend.services.ActorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;
    private final ShowActorMapper showActorMapper;

    @Override
    public ActorDetailsResponseDto getActorDetails(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actor not found with id: " + id));
        ActorDetailsResponseDto responseDto = actorMapper.toBaseDetailsDto(actor);

        List<ShowActorDto> showActorDtos = actor.getShowActors().stream().map(showActorMapper::toShowActorDto).toList();
        responseDto.setShows(showActorDtos);

        return responseDto;
    }
}

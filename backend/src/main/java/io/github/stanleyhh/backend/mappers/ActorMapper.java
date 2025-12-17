package io.github.stanleyhh.backend.mappers;

import io.github.stanleyhh.backend.domain.dtos.ActorDetailsResponseDto;
import io.github.stanleyhh.backend.domain.entities.Actor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ActorMapper {
    ActorDetailsResponseDto toBaseDetailsDto(Actor actor);
}

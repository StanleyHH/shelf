package io.github.stanleyhh.backend.mappers;

import io.github.stanleyhh.backend.domain.dtos.ActorRoleDto;
import io.github.stanleyhh.backend.domain.dtos.ShowActorDto;
import io.github.stanleyhh.backend.domain.entities.ShowActor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShowActorMapper {

    @Mapping(source = "actor.id", target = "id")
    @Mapping(source = "actor.name", target = "name")
    @Mapping(source = "actor.image", target = "image")
    @Mapping(source = "role", target = "role")
    ActorRoleDto toActorRoleDto(ShowActor showActor);

    @Mapping(source = "show.id", target = "id")
    @Mapping(source = "show.imageUrl", target = "image")
    @Mapping(source = "show.title", target = "title")
    @Mapping(source = "show.status", target = "status")
    ShowActorDto toShowActorDto(ShowActor showActor);
}
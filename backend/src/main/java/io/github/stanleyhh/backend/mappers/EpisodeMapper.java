package io.github.stanleyhh.backend.mappers;

import io.github.stanleyhh.backend.domain.dtos.EpisodeDetailsResponseDto;
import io.github.stanleyhh.backend.domain.dtos.EpisodeDto;
import io.github.stanleyhh.backend.domain.entities.Episode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EpisodeMapper {
    EpisodeDto toDto(Episode episode);

    @Mapping(target = "episodeNumber", source = "number")
    EpisodeDetailsResponseDto toBaseDetailsDto(Episode episode);
}

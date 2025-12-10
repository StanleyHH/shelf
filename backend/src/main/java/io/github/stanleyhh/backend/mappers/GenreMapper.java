package io.github.stanleyhh.backend.mappers;

import io.github.stanleyhh.backend.domain.dtos.GenreDto;
import io.github.stanleyhh.backend.domain.entities.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenreMapper {
    GenreDto toDto(Genre genre);
}

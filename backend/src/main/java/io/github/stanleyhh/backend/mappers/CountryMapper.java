package io.github.stanleyhh.backend.mappers;

import io.github.stanleyhh.backend.domain.dtos.CountryDto;
import io.github.stanleyhh.backend.domain.entities.Country;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CountryMapper {
    CountryDto toDto(Country country);
}

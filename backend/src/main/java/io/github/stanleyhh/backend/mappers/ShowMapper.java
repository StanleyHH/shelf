package io.github.stanleyhh.backend.mappers;

import io.github.stanleyhh.backend.domain.dtos.ShowListResponseDto;
import io.github.stanleyhh.backend.domain.entities.Country;
import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.domain.entities.Show;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShowMapper {

    @Mapping(target = "startYear", source = "firstAirDate", qualifiedByName = "getStartYear")
    @Mapping(target = "countries", source = "countries", qualifiedByName = "getCountriesIds")
    @Mapping(target = "genres", source = "genres", qualifiedByName = "getGenresIds")
    ShowListResponseDto toShowListResponseDto(Show show);

    @Named("getStartYear")
    default Integer getStartYear(LocalDate firstAirDate) {
        return firstAirDate.getYear();
    }

    @Named("getCountriesIds")
    default Set<Long> getCountriesIds(Set<Country> countries) {
        return countries.stream()
                .map(Country::getId)
                .collect(Collectors.toSet());
    }

    @Named("getGenresIds")
    default Set<Long> getGenresIds(Set<Genre> genres) {
        return genres.stream()
                .map(Genre::getId)
                .collect(Collectors.toSet());
    }
}

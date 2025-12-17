package io.github.stanleyhh.backend.domain.dtos;

import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowDetailsResponseDto {
    private Long id;
    private String title;
    private ShowStatus status;
    private LocalDate firstAirDate;
    private LocalDate lastAirDate;
    private String imageUrl;
    private String network;
    private float imdbRating;
    private String description;
    private List<SeasonDto> seasons;
    private List<CountryDto> countries;
    private List<GenreDto> genres;
    private List<ActorRoleDto> actors;
}

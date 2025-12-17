package io.github.stanleyhh.backend.domain.dtos;

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
public class ActorDetailsResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String biography;
    private String image;
    List<ShowActorDto> shows;
}

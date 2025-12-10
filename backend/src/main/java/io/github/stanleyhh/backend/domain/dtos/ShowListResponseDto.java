package io.github.stanleyhh.backend.domain.dtos;

import io.github.stanleyhh.backend.domain.entities.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowListResponseDto {
    private UUID id;
    private String title;
    private String originalTitle;
    private ShowStatus status;
    private Integer startYear;
    private String imageUrl;
    private Set<UUID> countries;
    private Set<UUID> genres;
}

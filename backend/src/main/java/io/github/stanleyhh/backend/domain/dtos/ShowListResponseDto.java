package io.github.stanleyhh.backend.domain.dtos;

import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowListResponseDto {
    private Long id;
    private String title;
    private String originalTitle;
    private ShowStatus status;
    private Integer startYear;
    private String imageUrl;
    private Set<Long> countries;
    private Set<Long> genres;
}

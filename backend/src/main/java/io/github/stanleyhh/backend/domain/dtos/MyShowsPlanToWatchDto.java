package io.github.stanleyhh.backend.domain.dtos;

import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyShowsPlanToWatchDto {
    private Long id;
    private String title;
    private ShowStatus status;
    private Integer totalSeasons;
}

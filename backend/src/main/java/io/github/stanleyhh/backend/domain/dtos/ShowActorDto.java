package io.github.stanleyhh.backend.domain.dtos;

import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowActorDto {
    private Long id;
    private String image;
    private String title;
    private ShowStatus status;
    private String role;
}

package io.github.stanleyhh.backend.domain.dtos;

import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShowDto {
    private UserShowStatus status;
    private Integer rating;
    private Set<UserShowEpisodeDto> watchedEpisodes;
}

package io.github.stanleyhh.backend.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyShowsDto {
    List<MyShowWatchingDto> watching;
    List<MyShowsPlanToWatchDto> planToWatch;
}

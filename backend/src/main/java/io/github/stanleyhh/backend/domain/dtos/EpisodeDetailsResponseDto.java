package io.github.stanleyhh.backend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpisodeDetailsResponseDto {
    private Long id;
    private Long episodeNumber;
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate releaseDate;

    private Integer runtime;
    private String image;
    private Long seasonNumber;
    private Double averageRating;
    private Integer averageRatingVotesCount;
    private Integer watchedBy;
    private String watchedByPercent;
    private ShowTitleDto show;
}
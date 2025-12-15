package io.github.stanleyhh.backend.domain.entities;

import io.github.stanleyhh.backend.domain.UserEpisodeId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user_episodes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEpisode {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private UserEpisodeId id;

    private Integer rating;

    @Column(name = "watched_date")
    private LocalDate watchedDate;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("episodeId")
    @JoinColumn(name = "episode_id", nullable = false)
    private Episode episode;
}

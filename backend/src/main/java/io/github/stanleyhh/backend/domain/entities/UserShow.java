package io.github.stanleyhh.backend.domain.entities;


import io.github.stanleyhh.backend.domain.entities.embeddable.UserShowId;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "user_shows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "show_id"})
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserShow {

    @EmbeddedId
    @Builder.Default
    @EqualsAndHashCode.Include
    private UserShowId id = new UserShowId();

    private Integer rating;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserShowStatus status;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("showId")
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;
}
package io.github.stanleyhh.backend.domain.entities;

import io.github.stanleyhh.backend.domain.entities.embeddable.ShowActorId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
        name = "show_actors",
        uniqueConstraints = @UniqueConstraint(columnNames = {"show_id", "actor_id"})
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShowActor {

    @EmbeddedId
    @Builder.Default
    @EqualsAndHashCode.Include
    private ShowActorId id = new ShowActorId();

    @Column(nullable = false)
    private String role;

    @ManyToOne
    @MapsId("showId")
    @JoinColumn(name = "show_id")
    private Show show;

    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(name = "actor_id")
    private Actor actor;
}

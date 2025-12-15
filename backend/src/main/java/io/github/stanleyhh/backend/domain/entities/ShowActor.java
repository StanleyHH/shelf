package io.github.stanleyhh.backend.domain.entities;

import io.github.stanleyhh.backend.domain.ShowActorId;
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

@Entity
@Table(name = "show_actors")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShowActor {

    @EmbeddedId
    @EqualsAndHashCode.Include
    private ShowActorId id;

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

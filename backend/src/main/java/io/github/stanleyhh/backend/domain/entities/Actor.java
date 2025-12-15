package io.github.stanleyhh.backend.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "actors")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actor_id_seq_gen")
    @SequenceGenerator(name = "actor_id_seq_gen", sequenceName = "actor_id_seq")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String gender;

    private LocalDate birthDate;

    private String biography;

    private String image;

    @ManyToMany(mappedBy = "actors")
    @EqualsAndHashCode.Exclude
    private Set<Show> show;
}

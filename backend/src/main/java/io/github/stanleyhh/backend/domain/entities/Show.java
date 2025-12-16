package io.github.stanleyhh.backend.domain.entities;

import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shows")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "show_id_seq_gen")
    @SequenceGenerator(name = "show_id_seq_gen", sequenceName = "show_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String originalTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShowStatus status;

    @Column(nullable = false)
    private LocalDate firstAirDate;

    private LocalDate lastAirDate;

    private String imageUrl;

    private String network;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<Season> seasons = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "show_countries",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Country> countries = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "show_genres",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @EqualsAndHashCode.Exclude
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<UserShow> userShows = new HashSet<>();

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<ShowActor> showActors = new HashSet<>();
}

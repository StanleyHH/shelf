package io.github.stanleyhh.backend.domain.entities;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "shows")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String originalTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ShowStatus status;

    @Column(nullable = false)
    private LocalDateTime firstAirDate;

    private LocalDateTime lastAirDate;

    private String image;

    @ManyToMany
    @JoinTable(
            name = "show_countries",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> countries = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "show_genres",
            joinColumns = @JoinColumn(name = "show_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Objects.equals(id, show.id) && Objects.equals(title, show.title)
                && Objects.equals(originalTitle, show.originalTitle) && status == show.status
                && Objects.equals(firstAirDate, show.firstAirDate) && Objects.equals(lastAirDate, show.lastAirDate)
                && Objects.equals(image, show.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, originalTitle, status, firstAirDate, lastAirDate, image);
    }
}

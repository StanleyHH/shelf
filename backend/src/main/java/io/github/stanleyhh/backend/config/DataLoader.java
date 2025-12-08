package io.github.stanleyhh.backend.config;

import io.github.stanleyhh.backend.domain.entities.Country;
import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.ShowStatus;
import io.github.stanleyhh.backend.repositories.CountryRepository;
import io.github.stanleyhh.backend.repositories.GenreRepository;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final ShowRepository showRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;

    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {

        if (showRepository.existsBy()) {
            log.info("Database already seeded");
            return;
        }

        Country germany = getOrCreateCountry("Germany");
        Country usa = getOrCreateCountry("USA");

        Genre action = getOrCreateGenre("action");
        Genre fantasy = getOrCreateGenre("fantasy");

        createRandomShows(List.of(germany, usa), List.of(action, fantasy));

        log.info("Database seeded successfully");
    }

    private Country getOrCreateCountry(String name) {
        return countryRepository.getCountryByName(name)
                .orElseGet(() -> countryRepository.save(Country.builder()
                        .name(name)
                        .build()));
    }

    private Genre getOrCreateGenre(String name) {
        return genreRepository.getGenreByName(name)
                .orElseGet(() -> genreRepository.save(Genre.builder()
                        .name(name)
                        .build()));
    }

    private void createRandomShows(List<Country> countries, List<Genre> genres) {
        for (int i = 1; i <= 10; i++) {
            Set<Country> showCountries = switch (i % 3) {
                case 0 -> Set.of(countries.get(0));
                case 1 -> Set.of(countries.get(0), countries.get(1));
                default -> Set.of(countries.get(1));
            };

            Set<Genre> showGenres = switch (i % 3) {
                case 0 -> Set.of(genres.get(0));
                case 1 -> Set.of(genres.get(0), genres.get(1));
                default -> Set.of(genres.get(1));
            };

            String title = faker.movie().name();

            Show show = Show.builder()
                    .countries(showCountries)
                    .genres(showGenres)
                    .title(title)
                    .originalTitle(title)
                    .status(ShowStatus.ONGOING)
                    .firstAirDate(LocalDateTime.now())
                    .lastAirDate(LocalDateTime.now())
                    .imageUrl("https://static.tvmaze.com/uploads/images/original_untouched/220/550800.jpg")
                    .build();

            showRepository.save(show);
        }
    }
}

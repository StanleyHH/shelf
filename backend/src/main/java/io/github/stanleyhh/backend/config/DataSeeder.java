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
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Profile("!test")
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final ShowRepository showRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;

    private final Faker faker = new Faker();

    private final List<Genre> genres = new ArrayList<>();
    private final List<Country> countries = new ArrayList<>();

    @Override
    public void run(String... args) {
        seedGenres();
        seedCountries();
        seedShows(50);
    }

    private void seedGenres() {
        if (genreRepository.existsBy()) {
            log.info("The genres table has already been seeded");
            return;
        }

        try {
            List<String> genreNames = Files.readAllLines(Paths.get(new ClassPathResource("seed/genres.txt").getURI()));
            genreNames.forEach(name -> {
                Genre genre = genreRepository.save(Genre.builder().name(name).build());
                genres.add(genre);
            });
            log.info("The genres table has been seeded successfully");
        } catch (IOException e) {
            log.warn("Can't get genres from the file");
        }
    }

    private void seedCountries() {
        if (countryRepository.existsBy()) {
            log.info("The countries table has already been seeded");
            return;
        }
        try {
            List<String> countryNames = Files.readAllLines(Paths.get(new ClassPathResource("seed/countries.txt").getURI()));
            countryNames.forEach(name -> {
                Country country = countryRepository.save(Country.builder().name(name).build());
                countries.add(country);
            });
            log.info("The countries table has been seeded successfully");
        } catch (IOException e) {
            log.warn("Can't get countries from the file");
        }
    }

    private void seedShows(int quantity) {
        if (showRepository.existsBy()) {
            log.info("The shows table has already been seeded");
            return;
        }

        List<String> images;

        try {
            images = Files.readAllLines(Paths.get(new ClassPathResource("seed/images.txt").getURI()));
        } catch (IOException e) {
            log.warn("Can't get images from the file");
            return;
        }

        for (int i = 0; i < quantity; i++) {
            Collections.shuffle(genres);
            Collections.shuffle(countries);
            Collections.shuffle(images);

            List<ShowStatus> statuses = Arrays.asList(ShowStatus.values());
            Collections.shuffle(statuses);

            String title = faker.movie().name();

            LocalDate randomDate = LocalDate.ofEpochDay(
                    ThreadLocalRandom.current().nextLong(
                            LocalDate.of(1975, 1, 1).toEpochDay(),
                            LocalDate.of(2025, 1, 1).toEpochDay() + 1
                    )
            );

            Show show = Show.builder()
                    .countries(Set.of(countries.get(0), countries.get(1)))
                    .genres(Set.of(genres.get(0), genres.get(1)))
                    .title(title)
                    .originalTitle(title)
                    .status(statuses.getFirst())
                    .firstAirDate(randomDate)
                    .lastAirDate(LocalDate.now())
                    .imageUrl(images.getFirst())
                    .build();

            showRepository.save(show);
        }
        log.info("The shows table has been seeded successfully");
    }
}

package io.github.stanleyhh.backend.config;

import io.github.stanleyhh.backend.domain.entities.Actor;
import io.github.stanleyhh.backend.domain.entities.Country;
import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.domain.entities.Season;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.ShowActor;
import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import io.github.stanleyhh.backend.repositories.ActorRepository;
import io.github.stanleyhh.backend.repositories.CountryRepository;
import io.github.stanleyhh.backend.repositories.GenreRepository;
import io.github.stanleyhh.backend.repositories.SeasonRepository;
import io.github.stanleyhh.backend.repositories.ShowActorRepository;
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

@Component
@Profile("!test")
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final ShowRepository showRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final ShowActorRepository showActorRepository;
    private final SeasonRepository seasonRepository;

    private final Faker faker = new Faker();

    private final List<Genre> genres = new ArrayList<>();
    private final List<Country> countries = new ArrayList<>();
    private final List<Actor> actors = new ArrayList<>();
    private final List<Show> shows = new ArrayList<>();

    private static final Integer SHOWS_QUANTITY = 50;

    @Override
    public void run(String... args) {
        seedActors();
        seedGenres();
        seedCountries();
        seedShows();
        seedShowActors();
        seedSeasons();
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

    private void seedActors() {
        if (actorRepository.existsBy()) {
            log.info("The actors table has already been seeded");
            return;
        }

        try {
            List<String> actorImages = Files.readAllLines(Paths.get(new ClassPathResource("seed/actor_images.txt").getURI()));
            actorImages.forEach(image -> {
                Actor actor = actorRepository.save(Actor.builder()
                        .image(image)
                        .name(faker.name().fullName())
                        .biography(faker.lorem().paragraph(10))
                        .gender(faker.gender().binaryTypes())
                        .birthDate(faker.timeAndDate().birthday())
                        .build());
                actors.add(actor);
            });
            log.info("The actors table has been seeded successfully");

        } catch (IOException e) {
            log.warn("Can't get actor images from the file");
        }
    }

    private void seedShows() {
        if (showRepository.existsBy()) {
            log.info("The shows table has already been seeded");
            return;
        }

        List<String> images;

        try {
            images = Files.readAllLines(Paths.get(new ClassPathResource("seed/show_images.txt").getURI()));
        } catch (IOException e) {
            log.warn("Can't get images from the file");
            return;
        }

        for (int i = 0; i < SHOWS_QUANTITY; i++) {
            Collections.shuffle(genres);
            Collections.shuffle(countries);
            Collections.shuffle(images);

            List<ShowStatus> statuses = Arrays.asList(ShowStatus.values());
            Collections.shuffle(statuses);

            String title = faker.movie().name();

            Show show = Show.builder()
                    .countries(Set.of(countries.get(0), countries.get(1)))
                    .genres(Set.of(genres.get(0), genres.get(1)))
                    .title(title)
                    .originalTitle(title)
                    .status(statuses.getFirst())
                    .firstAirDate(faker.timeAndDate().birthday())
                    .lastAirDate(LocalDate.now())
                    .imageUrl(images.getFirst())
                    .network(faker.animal().name())
                    .description(faker.lorem().paragraph(10))
                    .build();

            Show savedShow = showRepository.save(show);
            shows.add(savedShow);
        }
        log.info("The shows table has been seeded successfully");
    }

    private void seedShowActors() {
        if (showActorRepository.existsBy()) {
            log.info("The show_actors table has already been seeded");
            return;
        }

        if (shows.isEmpty()) {
            shows.addAll(showRepository.findAll());
        }

        for (Show show : shows) {
            Collections.shuffle(actors);
            for (int j = 0; j < faker.random().nextInt(4, 10); j++) {
                Actor actor = actors.get(j);
                ShowActor showActor = ShowActor
                        .builder()
                        .show(show)
                        .actor(actor)
                        .role(faker.backToTheFuture().character())
                        .build();
                showActorRepository.save(showActor);
            }
        }
        log.info("The show_actors table has been seeded successfully");
    }

    private void seedSeasons() {
        if (seasonRepository.existsBy()) {
            log.info("The seasons table has already been seeded");
            return;
        }

        if (shows.isEmpty()) {
            shows.addAll(showRepository.findAll());
        }

        for (Show show : shows) {
            for (long i = 1; i <= faker.random().nextLong(2, 6); i++) {
                Season season = Season.builder().number(i).show(show).build();
                seasonRepository.save(season);
            }
        }
        log.info("The seasons table has been seeded successfully");
    }
}

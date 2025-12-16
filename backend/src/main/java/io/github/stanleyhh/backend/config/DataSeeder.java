package io.github.stanleyhh.backend.config;

import io.github.stanleyhh.backend.domain.entities.Actor;
import io.github.stanleyhh.backend.domain.entities.Country;
import io.github.stanleyhh.backend.domain.entities.Episode;
import io.github.stanleyhh.backend.domain.entities.Genre;
import io.github.stanleyhh.backend.domain.entities.Season;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.ShowActor;
import io.github.stanleyhh.backend.domain.entities.User;
import io.github.stanleyhh.backend.domain.entities.UserEpisode;
import io.github.stanleyhh.backend.domain.entities.UserShow;
import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import io.github.stanleyhh.backend.domain.enums.UserShowStatus;
import io.github.stanleyhh.backend.repositories.ActorRepository;
import io.github.stanleyhh.backend.repositories.CountryRepository;
import io.github.stanleyhh.backend.repositories.EpisodeRepository;
import io.github.stanleyhh.backend.repositories.GenreRepository;
import io.github.stanleyhh.backend.repositories.SeasonRepository;
import io.github.stanleyhh.backend.repositories.ShowActorRepository;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import io.github.stanleyhh.backend.repositories.UserEpisodeRepository;
import io.github.stanleyhh.backend.repositories.UserRepository;
import io.github.stanleyhh.backend.repositories.UserShowRepository;
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
    private final EpisodeRepository episodeRepository;
    private final UserRepository userRepository;
    private final UserShowRepository userShowRepository;
    private final UserEpisodeRepository userEpisodeRepository;

    private final Faker faker = new Faker();

    private final List<Genre> genres = new ArrayList<>();
    private final List<Country> countries = new ArrayList<>();
    private final List<Actor> actors = new ArrayList<>();
    private final List<Show> shows = new ArrayList<>();
    private final List<Season> seasons = new ArrayList<>();
    private final List<Episode> episodes = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<UserShow> userShows = new ArrayList<>();

    private static final Integer SHOWS_QUANTITY = 10;
    private static final Integer USERS_QUANTITY = 10;

    List<String> images = new ArrayList<>();

    @Override
    public void run(String... args) {
        seedActors();
        seedGenres();
        seedCountries();
        seedShows();
        seedShowActors();
        seedSeasons();
        seedEpisodes();
        seedUsers();
        seedUserShows();
        seedUserEpisodes();
        log.info("LOS");
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

        getShowImages();

        if (images.isEmpty()) {
            return;
        }

        for (int i = 0; i < SHOWS_QUANTITY; i++) {
            Collections.shuffle(genres);
            Collections.shuffle(countries);
            Collections.shuffle(images);

            List<ShowStatus> statuses = Arrays.asList(ShowStatus.values());
            Collections.shuffle(statuses);

            String title = faker.movie().name();
            float imdbRating = ((float) faker.random().nextInt(0, 100)) / 10;

            Show show = Show.builder()
                    .countries(Set.of(countries.get(0), countries.get(1)))
                    .genres(Set.of(genres.get(0), genres.get(1)))
                    .title(title)
                    .originalTitle(title)
                    .status(statuses.getFirst())
                    .firstAirDate(faker.timeAndDate().birthday())
                    .lastAirDate(LocalDate.now())
                    .imageUrl(images.getFirst())
                    .network(faker.name().firstName())
                    .imdbRating(imdbRating)
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

        if (actors.isEmpty()) {
            actors.addAll(actorRepository.findAll());
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
                seasons.add(season);
            }
        }
        log.info("The seasons table has been seeded successfully");
    }

    private void seedEpisodes() {
        if (episodeRepository.existsBy()) {
            log.info("The episodes table has already been seeded");
            return;
        }

        if (seasons.isEmpty()) {
            seasons.addAll(seasonRepository.findAll());
        }

        getShowImages();

        if (images.isEmpty()) {
            return;
        }

        LocalDate firstEpisodeDate = faker.timeAndDate().birthday();

        for (Season season : seasons) {
            firstEpisodeDate = firstEpisodeDate.plusYears(1);
            LocalDate date = firstEpisodeDate;
            for (long i = 1; i <= faker.random().nextLong(5, 11); i++) {
                Collections.shuffle(images);
                date = date.plusDays(7);
                Episode episode = Episode.builder()
                        .number(i)
                        .title(faker.movie().quote())
                        .releaseDate(date)
                        .runtime(faker.random().nextInt(25, 60))
                        .image(images.getFirst())
                        .season(season)
                        .build();
                Episode savedEpisode = episodeRepository.save(episode);
                episodes.add(savedEpisode);
            }
        }
        log.info("The episodes table has been seeded successfully");
    }

    private void seedUsers() {
        if (userRepository.existsBy()) {
            log.info("The users table has already been seeded");
            return;
        }
        for (int i = 0; i < USERS_QUANTITY; i++) {
            User user = userRepository.save(new User());
            users.add(user);
        }
        log.info("The users table has been seeded successfully");
    }

    private void seedUserShows() {
        if (userShowRepository.existsBy()) {
            log.info("The user_shows table has already been seeded");
            return;
        }
        if (users.isEmpty()) {
            users.addAll(userRepository.findAll());
        }
        if (shows.isEmpty()) {
            shows.addAll(showRepository.findAll());
        }
        for (User user : users) {
            Collections.shuffle(shows);
            for (int i = 0; i < faker.random().nextInt(1, 4); i++) {
                UserShowStatus[] statuses = UserShowStatus.values();
                UserShow userShow = UserShow.builder()
                        .rating(faker.random().nextInt(1, 5))
                        .status(statuses[faker.random().nextInt(0, 3)])
                        .show(shows.get(i))
                        .user(user)
                        .build();
                userShowRepository.save(userShow);
            }
        }
        log.info("The user_shows table has been seeded successfully");
    }

    private void seedUserEpisodes() {
        if (userEpisodeRepository.existsBy()) {
            log.info("The user_episodes table has already been seeded");
            return;
        }
        if (users.isEmpty()) {
            users.addAll(userRepository.findAll());
        }
        if (episodes.isEmpty()) {
            episodes.addAll(episodeRepository.findAll());
        }
        if (userShows.isEmpty()) {
            userShows.addAll(userShowRepository.findAll());
        }
        for (User user : users) {
            List<UserShow> currentUserShows = userShows.stream()
                    .filter(userShow -> userShow.getUser().getId().equals(user.getId()))
                    .toList();
            List<Season> seasonList = currentUserShows.stream()
                    .flatMap(userShow -> seasonRepository.findAllByShowId(userShow.getShow().getId()).stream())
                    .toList();
            List<Episode> episodeList = seasonList.stream()
                    .flatMap(season -> episodeRepository.findAllBySeasonId(season.getId()).stream())
                    .toList();
            List<Episode> shuffled = new ArrayList<>(episodeList);
            Collections.shuffle(shuffled);

            for (int i = 0; i < 5; i++) {
                UserEpisode userEpisode = UserEpisode
                        .builder()
                        .rating(faker.random().nextInt(1, 5))
                        .watchedDate(faker.timeAndDate().birthday())
                        .episode(shuffled.get(i))
                        .user(user)
                        .build();
                userEpisodeRepository.save(userEpisode);
            }
            log.info("The user_episodes table has been seeded successfully");
        }
    }

    private void getShowImages() {
        try {
            images = Files.readAllLines(Paths.get(new ClassPathResource("seed/show_images.txt").getURI()));
        } catch (IOException e) {
            log.warn("Can't get images from the file");
        }
    }
}

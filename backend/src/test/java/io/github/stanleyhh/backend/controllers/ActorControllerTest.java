package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.entities.Actor;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.ShowActor;
import io.github.stanleyhh.backend.domain.entities.embeddable.ShowActorId;
import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import io.github.stanleyhh.backend.repositories.ActorRepository;
import io.github.stanleyhh.backend.repositories.ShowActorRepository;
import io.github.stanleyhh.backend.repositories.ShowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ActorControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private ShowActorRepository showActorRepository;

    @Test
    void getActorDetails_shouldReturn200AndCorrectBody_whenIdExists() throws Exception {

        Show show = Show.builder()
                .title("Terminator")
                .originalTitle("Terminator")
                .status(ShowStatus.ONGOING)
                .imageUrl("showImage")
                .firstAirDate(LocalDate.now())
                .build();
        show = showRepository.save(show);

        Actor actor = Actor.builder()
                .name("actorName")
                .gender("male")
                .birthDate(LocalDate.of(1980, 1, 1))
                .biography("very good")
                .image("actorImage")
                .build();
        actor = actorRepository.save(actor);

        ShowActor showActor = ShowActor.builder()
                .id(new ShowActorId(show.getId(), actor.getId()))
                .role("Hero")
                .show(show)
                .actor(actor)
                .build();

        showActorRepository.save(showActor);

        mockMvc.perform(get("/api/people/{id}", actor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(actor.getId()))
                .andExpect(jsonPath("$.name").value("actorName"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.biography").value("very good"))
                .andExpect(jsonPath("$.image").value("actorImage"))
                .andExpect(jsonPath("$.birthDate").value("1980-01-01"))
                .andExpect(jsonPath("$.shows").isArray())
                .andExpect(jsonPath("$.shows.length()").value(1))
                .andExpect(jsonPath("$.shows[0].title").value("Terminator"))
                .andExpect(jsonPath("$.shows[0].image").value("showImage"))
                .andExpect(jsonPath("$.shows[0].status").value("ONGOING"))
                .andExpect(jsonPath("$.shows[0].role").value("Hero"));
    }
}
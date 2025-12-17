package io.github.stanleyhh.backend.services.impl;

import io.github.stanleyhh.backend.domain.dtos.ActorDetailsResponseDto;
import io.github.stanleyhh.backend.domain.dtos.ShowActorDto;
import io.github.stanleyhh.backend.domain.entities.Actor;
import io.github.stanleyhh.backend.domain.entities.Show;
import io.github.stanleyhh.backend.domain.entities.ShowActor;
import io.github.stanleyhh.backend.domain.entities.embeddable.ShowActorId;
import io.github.stanleyhh.backend.domain.enums.ShowStatus;
import io.github.stanleyhh.backend.mappers.ActorMapper;
import io.github.stanleyhh.backend.mappers.ShowActorMapper;
import io.github.stanleyhh.backend.repositories.ActorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorServiceImplTest {

    @Mock
    ActorRepository actorRepository;

    @Spy
    ActorMapper actorMapper = Mappers.getMapper(ActorMapper.class);

    @Spy
    ShowActorMapper showActorMapper = Mappers.getMapper(ShowActorMapper.class);

    @InjectMocks
    ActorServiceImpl actorService;

    @Test
    void getActorDetails_shouldReturnCorrectDto_whenIdExists() {
        Show show = Show.builder()
                .id(25L)
                .title("Terminator")
                .status(ShowStatus.ONGOING)
                .imageUrl("showImage")
                .build();

        Actor actor = Actor.builder()
                .id(10L)
                .name("actorName")
                .gender("male")
                .birthDate(LocalDate.of(1980, 1, 1))
                .biography("very good")
                .image("actorImage")
                .build();

        ShowActor showActor = ShowActor.builder()
                .id(new ShowActorId(show.getId(), actor.getId()))
                .role("Hero")
                .show(show)
                .actor(actor)
                .build();

        actor.setShowActors(Set.of(showActor));

        when(actorRepository.findById(10L))
                .thenReturn(Optional.of(actor));

        ActorDetailsResponseDto result =
                actorService.getActorDetails(10L);

        assertEquals(10L, result.getId());
        assertEquals("actorName", result.getName());
        assertEquals("male", result.getGender());
        assertEquals("very good", result.getBiography());
        assertEquals("actorImage", result.getImage());

        assertEquals(1, result.getShows().size());

        ShowActorDto showDto = result.getShows().getFirst();
        assertEquals("Terminator", showDto.getTitle());
        assertEquals("showImage", showDto.getImage());
        assertEquals(ShowStatus.ONGOING, showDto.getStatus());
        assertEquals("Hero", showDto.getRole());
    }
}
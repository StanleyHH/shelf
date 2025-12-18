package io.github.stanleyhh.backend.controllers;

import io.github.stanleyhh.backend.domain.dtos.ActorDetailsResponseDto;
import io.github.stanleyhh.backend.services.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/people")
@RequiredArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @GetMapping(path = "/{actor_id}")
    ResponseEntity<ActorDetailsResponseDto> getActorDetails(@PathVariable("actor_id") Long actorId) {
        return ResponseEntity.ok(actorService.getActorDetails(actorId));
    }
}

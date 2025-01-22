package br.com.compass.eventmanagement.controllers;

import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.domain.event.dtos.EventRequestDto;
import br.com.compass.eventmanagement.domain.event.dtos.EventResponseDto;
import br.com.compass.eventmanagement.domain.event.mapper.EventMapper;
import br.com.compass.eventmanagement.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/br/com/compass/eventmanagement/v1")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/create-event")
    public ResponseEntity<EventResponseDto> insert(@RequestBody @Valid EventRequestDto body){
        Event event = eventService.insert(body);

        return ResponseEntity.ok().body(EventMapper.toDto(event));
    }
}

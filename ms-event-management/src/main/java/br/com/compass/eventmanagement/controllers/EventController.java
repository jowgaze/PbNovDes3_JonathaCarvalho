package br.com.compass.eventmanagement.controllers;

import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.domain.event.dtos.EventRequestDto;
import br.com.compass.eventmanagement.domain.event.dtos.EventResponseDto;
import br.com.compass.eventmanagement.domain.event.mapper.EventMapper;
import br.com.compass.eventmanagement.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/br/com/compass/eventmanagement/v1")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/create-event")
    public ResponseEntity<EventResponseDto> insert(@RequestBody @Valid EventRequestDto request) {
        log.info("Request to create event: {}", request);
        Event event = eventService.insert(request);
        EventResponseDto response = EventMapper.toDto(event);
        log.info("Event created successfully. id: {}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/get-event/{id}")
    public ResponseEntity<EventResponseDto> findById(@PathVariable("id") String id){
        log.info("Request to get event by id: {}", id);
        Event event = eventService.findById(id);
        EventResponseDto response = EventMapper.toDto(event);
        log.info("Event found: {}", response);
        return ResponseEntity.ok().body(response);
    }
}

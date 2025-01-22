package br.com.compass.eventmanagement.controllers;

import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.domain.event.dtos.EventRequestDto;
import br.com.compass.eventmanagement.domain.event.dtos.EventResponseDto;
import br.com.compass.eventmanagement.domain.event.dtos.EventUpdateRequestDto;
import br.com.compass.eventmanagement.domain.event.mapper.EventMapper;
import br.com.compass.eventmanagement.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get-all-events")
    public ResponseEntity<List<EventResponseDto>> findAll(){
        log.info("Request to get all events");
        List<Event> eventList = eventService.findAll();
        List<EventResponseDto> responseList = EventMapper.toListDto(eventList);
        log.info("All events recovered successfully. listSize: {}", responseList.size());
        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<EventResponseDto>> findAllSorted(@RequestParam(defaultValue = "asc") String direction){
        log.info("Request to get all events sorted");
        List<Event> eventList = eventService.findAllSorted(direction);
        List<EventResponseDto> responseList = EventMapper.toListDto(eventList);
        log.info("All events sorted recovered successfully. listSize: {}", responseList.size());
        return ResponseEntity.ok().body(responseList);
    }

    @PutMapping("/update-event/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody @Valid EventUpdateRequestDto request){
        log.info("Request to update event: id: {}", id);
        eventService.update(id, request);
        log.info("Event updated successfully. id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        log.info("Request to delete event. id: {}", id);
        eventService.delete(id);
        log.info("event deleted successfully");
        return ResponseEntity.noContent().build();
    }
}

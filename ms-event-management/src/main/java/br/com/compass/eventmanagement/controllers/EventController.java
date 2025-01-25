package br.com.compass.eventmanagement.controllers;

import br.com.compass.eventmanagement.controllers.exception.StandardError;
import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.dtos.event.EventRequestDto;
import br.com.compass.eventmanagement.dtos.event.EventResponseDto;
import br.com.compass.eventmanagement.dtos.event.EventUpdateRequestDto;
import br.com.compass.eventmanagement.dtos.event.HasTicketsDto;
import br.com.compass.eventmanagement.dtos.event.mapper.EventMapper;
import br.com.compass.eventmanagement.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Events")
public class EventController {
    private final EventService eventService;

    @Operation(
            summary = "Create a new event",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Event created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Address not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Field validation error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            }
    )
    @PostMapping("/create-event")
    public ResponseEntity<EventResponseDto> insert(@RequestBody @Valid EventRequestDto request) {
        log.info("Request to create event: {}", request);
        Event event = eventService.insert(request);
        EventResponseDto response = EventMapper.toDto(event);
        log.info("Event created successfully. id: {}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    @Operation(
            summary = "Retrieve a event by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            }
    )
    @GetMapping("/get-event/{id}")
    public ResponseEntity<EventResponseDto> findById(@PathVariable("id") String id) {
        log.info("Request to get event. id: {}", id);
        Event event = eventService.findById(id);
        EventResponseDto response = EventMapper.toDto(event);
        log.info("Event found: {}", response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "Retrieve all events",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDto.class)))
            }
    )
    @GetMapping("/get-all-events")
    public ResponseEntity<List<EventResponseDto>> findAll() {
        log.info("Request to get all events");
        List<Event> eventList = eventService.findAll();
        List<EventResponseDto> responseList = EventMapper.toListDto(eventList);
        log.info("All events recovered successfully. listSize: {}", responseList.size());
        return ResponseEntity.ok().body(responseList);
    }

    @Operation(
            summary = "Retrieve all events sorted",
            parameters = @Parameter(
                    in = ParameterIn.QUERY,
                    name = "direction",
                    content = @Content(schema = @Schema(type = "string", defaultValue = "asc")),
                    description = "asc or desc"
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Events sorted retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponseDto.class)))
            }
    )
    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<EventResponseDto>> findAllSorted(@RequestParam(defaultValue = "asc") String direction) {
        log.info("Request to get all events sorted");
        List<Event> eventList = eventService.findAllSorted(direction);
        List<EventResponseDto> responseList = EventMapper.toListDto(eventList);
        log.info("All events sorted recovered successfully. listSize: {}", responseList.size());
        return ResponseEntity.ok().body(responseList);
    }

    @Operation(
            summary = "Update a event by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Event updated successfully"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event or new address not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Has linked tickets",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HasTicketsDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Field validation error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))
                    ),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Ticket service unavailable",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))
                    )
            }
    )

    @PutMapping("/update-event/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody @Valid EventUpdateRequestDto request) {
        log.info("Request to update event. id: {}", id);
        eventService.update(id, request);
        log.info("Event updated successfully. id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete a event by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Event deleted successfully"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Has linked tickets",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HasTicketsDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Ticket service unavailable",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))
                    )
            }
    )
    @DeleteMapping("/delete-event/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        log.info("Request to delete event. id: {}", id);
        eventService.delete(id);
        log.info("event deleted successfully");
        return ResponseEntity.noContent().build();
    }
}

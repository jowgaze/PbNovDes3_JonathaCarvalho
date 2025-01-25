package br.com.compass.ticketmanagement.controllers;

import br.com.compass.ticketmanagement.controllers.exception.StandardError;
import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import br.com.compass.ticketmanagement.dtos.event.HasTicketsDto;
import br.com.compass.ticketmanagement.dtos.ticket.TicketRequestDto;
import br.com.compass.ticketmanagement.dtos.ticket.TicketResponseDto;
import br.com.compass.ticketmanagement.dtos.ticket.TicketUpdateRequestDto;
import br.com.compass.ticketmanagement.dtos.ticket.mapper.TicketMapper;
import br.com.compass.ticketmanagement.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("br/com/compass/ticketmanagement/v1")
@RequiredArgsConstructor
@Tag(name = "Tickets")
public class TicketController {
    private final TicketService ticketService;


    @Operation(
            summary = "Create a new ticket",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Ticket created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Field validation error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Event service unavailable",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            }
    )
    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDto> insert(@RequestBody @Valid TicketRequestDto request){
        log.info("Request to create ticket: {}", request);
        Ticket ticket = TicketMapper.toEntity(request);
        TicketResponseDto response = ticketService.insert(ticket);
        log.info("Ticket created successfully. id: {}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    @Operation(
            summary = "Retrieve a ticket by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ticket retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ticket or event not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Event service unavailable",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            }
    )
    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDto> findById(@PathVariable("id") String id){
        log.info("Request to get ticket. id: {}", id);
        TicketResponseDto response = ticketService.findFullById(id);
        log.info("Ticket found: {}", response);
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "Retrieve a tickets by CPF",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tickets retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponseDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ticket or event not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Event service unavailable",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            }
    )
    @GetMapping("/get-ticket-by-cpf/{cpf}")
    public ResponseEntity<List<TicketResponseDto>> findByCpf(@PathVariable("cpf") @CPF String cpf){
        log.info("Request to get tickets. cpf: {}", cpf);
        List<TicketResponseDto> response = ticketService.findByCpf(cpf);
        log.info("Tickets found: size: {}", response.size());
        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "Update a ticket by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Ticket updated successfully"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ticket not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Field validation error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class)))
            }
    )
    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody @Valid TicketUpdateRequestDto request){
        log.info("Request to update ticket. id: {}", id);
        ticketService.update(id,  request);
        log.info("Ticket updated successfully. id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Cancel a ticket by ID (Soft delete)",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Ticket canceled successfully"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ticket not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))),
            }
    )
    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable("id") String id){
        log.info("Request to cancel ticket. id: {}", id);
        ticketService.softDelete(id);
        log.info("ticket canceled successfully");
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Check if event has ticket",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Event checked successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = HasTicketsDto.class)))
            }
    )
    @GetMapping("/check-tickets-by-event/{eventId}")
    public ResponseEntity<HasTicketsDto> hasTickets(@PathVariable("eventId") String eventId){
        log.info("Request to check if the event has a linked ticket. idEvent: {}", eventId);
        HasTicketsDto response = ticketService.hasTickets(eventId);
        log.info("request to check finished");
        return ResponseEntity.ok().body(response);
    }
}

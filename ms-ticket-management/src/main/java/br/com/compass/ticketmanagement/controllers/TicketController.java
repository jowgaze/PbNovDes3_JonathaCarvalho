package br.com.compass.ticketmanagement.controllers;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import br.com.compass.ticketmanagement.domain.ticket.dtos.HasTicketsDto;
import br.com.compass.ticketmanagement.domain.ticket.dtos.TicketRequestDto;
import br.com.compass.ticketmanagement.domain.ticket.dtos.TicketResponseDto;
import br.com.compass.ticketmanagement.domain.ticket.dtos.TicketUpdateRequestDto;
import br.com.compass.ticketmanagement.domain.ticket.mapper.TicketMapper;
import br.com.compass.ticketmanagement.services.TicketService;
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
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/create-ticket")
    public ResponseEntity<TicketResponseDto> insert(@RequestBody @Valid TicketRequestDto request){
        log.info("Request to create ticket: {}", request);
        Ticket ticket = TicketMapper.toEntity(request);
        TicketResponseDto response = ticketService.insert(ticket);
        log.info("Ticket created successfully. id: {}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<TicketResponseDto> findById(@PathVariable("id") String id){
        log.info("Request to get ticket. id: {}", id);
        TicketResponseDto response = ticketService.findFullById(id);
        log.info("Ticket found: {}", response);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get-ticket-by-cpf/{cpf}")
    public ResponseEntity<List<TicketResponseDto>> findByCpf(@PathVariable("cpf") @CPF String cpf){
        log.info("Request to get tickets. cpf: {}", cpf);
        List<TicketResponseDto> response = ticketService.findByCpf(cpf);
        log.info("Tickets found: size: {}", response.size());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update-ticket/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody @Valid TicketUpdateRequestDto request){
        log.info("Request to update ticket. id: {}", id);
        ticketService.update(id,  request);
        log.info("Ticket updated successfully. id: {}", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable("id") String id){
        log.info("Request to cancel ticket. id: {}", id);
        ticketService.softDelete(id);
        log.info("ticket canceled successfully");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-tickets-by-event/{eventId}")
    public ResponseEntity<HasTicketsDto> hasTickets(@PathVariable("eventId") String eventId){
        log.info("Request to check if the event has a linked ticket. idEvent: {}", eventId);
        HasTicketsDto response = ticketService.hasTickets(eventId);
        log.info("request to check finished");
        return ResponseEntity.ok().body(response);
    }
}

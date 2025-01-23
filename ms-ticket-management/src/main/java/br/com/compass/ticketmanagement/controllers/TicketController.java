package br.com.compass.ticketmanagement.controllers;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import br.com.compass.ticketmanagement.domain.ticket.dtos.TicketRequestDto;
import br.com.compass.ticketmanagement.domain.ticket.dtos.TicketResponseDto;
import br.com.compass.ticketmanagement.domain.ticket.mapper.TicketMapper;
import br.com.compass.ticketmanagement.services.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

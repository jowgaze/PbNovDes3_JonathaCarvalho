package br.com.compass.ticketmanagement.services;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import br.com.compass.ticketmanagement.domain.ticket.dtos.TicketResponseDto;
import br.com.compass.ticketmanagement.domain.ticket.mapper.TicketMapper;
import br.com.compass.ticketmanagement.repositories.TicketRepository;
import br.com.compass.ticketmanagement.services.client.dtos.EventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventService eventService;

    @Transactional
    public TicketResponseDto insert(Ticket ticket) {
        ticket.setId(generateId());
        TicketResponseDto response = getTicketFull(ticket);
        ticketRepository.save(ticket);
        return response;
    }

    private String generateId() {
        return Long.toString(ticketRepository.count() + 1);
    }

    private TicketResponseDto getTicketFull(Ticket ticket) {
        String eventId = ticket.getEventId();
        EventResponseDto event = eventService.findById(eventId);
        return TicketMapper.toDto(ticket, event);
    }
}

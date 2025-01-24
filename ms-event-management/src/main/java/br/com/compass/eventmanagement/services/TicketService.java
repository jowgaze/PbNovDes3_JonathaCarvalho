package br.com.compass.eventmanagement.services;

import br.com.compass.eventmanagement.services.client.ticket.TicketClient;
import br.com.compass.eventmanagement.dtos.event.HasTicketsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketClient ticketClient;

    public boolean hasTickets(String eventId){
        HasTicketsDto hasTickets = ticketClient.hasTickets(eventId);
        return hasTickets.getHasTickets();
    }
}

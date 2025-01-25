package br.com.compass.eventmanagement.services;

import br.com.compass.eventmanagement.exceptions.TicketServiceException;
import br.com.compass.eventmanagement.services.client.ticket.TicketClient;
import br.com.compass.eventmanagement.dtos.event.HasTicketsDto;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketClient ticketClient;

    public boolean hasTickets(String eventId){
        try {
            HasTicketsDto hasTickets = ticketClient.hasTickets(eventId);
            return hasTickets.getHasTickets();
        } catch (RetryableException e){
            log.error("Ticket service unavailable");
            throw new TicketServiceException("ticket service unavailable");
        }
    }
}

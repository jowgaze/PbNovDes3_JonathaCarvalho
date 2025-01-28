package br.com.compass.eventmanagement.services.client.ticket;

import br.com.compass.eventmanagement.dtos.event.HasTicketsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-ticket", url = "${feign.ticket.url}/br/com/compass/ticketmanagement/v1")
public interface TicketClient {

    @GetMapping("/check-tickets-by-event/{eventId}")
    HasTicketsDto hasTickets(@PathVariable("eventId") String eventId);
}

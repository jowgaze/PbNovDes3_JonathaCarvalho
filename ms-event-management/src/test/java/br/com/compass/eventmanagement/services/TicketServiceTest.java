package br.com.compass.eventmanagement.services;

import br.com.compass.eventmanagement.dtos.event.HasTicketsDto;
import br.com.compass.eventmanagement.services.client.ticket.TicketClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketClient client;

    @InjectMocks
    private TicketService service;

    @Test
    public void hasTickets_EventHasTicket_ReturnsBoolean(){
        HasTicketsDto hasTickets = new HasTicketsDto("eventId", true);
        when(client.hasTickets("eventId")).thenReturn(hasTickets);

        boolean sut = service.hasTickets("eventId");

        assertThat(sut).isTrue();
    }
}

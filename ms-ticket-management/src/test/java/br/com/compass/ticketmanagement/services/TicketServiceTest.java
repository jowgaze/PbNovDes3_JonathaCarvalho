package br.com.compass.ticketmanagement.services;

import br.com.compass.ticketmanagement.dtos.event.HasTicketsDto;
import br.com.compass.ticketmanagement.dtos.ticket.TicketResponseDto;
import br.com.compass.ticketmanagement.exceptions.TicketNotFoundException;
import br.com.compass.ticketmanagement.repositories.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.compass.ticketmanagement.common.EventConstants.*;
import static br.com.compass.ticketmanagement.common.TicketConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(SpringExtension.class)
public class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;

    @Mock
    private EventService eventService;

    @Mock
    private TicketRepository ticketRepository;

    @Test
    public void createTicket_WithValidRequest_ReturnsTicketResponse(){
        when(eventService.findById("eventId")).thenReturn(EVENT);

        TicketResponseDto sut = ticketService.insert(TICKET);

        assertThat(sut).isNotNull();
        assertThat(sut.getId()).isEqualTo(TICKET_RESPONSE.getId());
        assertThat(sut.getCpf()).isEqualTo(TICKET_RESPONSE.getCpf());
        assertThat(sut.getEvent().getId()).isEqualTo(TICKET_RESPONSE.getEvent().getId());
    }

    @Test
    public void getFullTicket_ByExistingId_ReturnsTicketResponse(){
        String ticketId = "1";
        when(ticketRepository.findByIdAndDeletedFalse(ticketId)).thenReturn(Optional.of(TICKET_SAVED));
        when(eventService.findById("eventId")).thenReturn(EVENT);

        TicketResponseDto sut = ticketService.findFullById(ticketId);

        assertThat(sut).isNotNull();
        assertThat(sut.getId()).isEqualTo(TICKET_RESPONSE.getId());
        assertThat(sut.getCpf()).isEqualTo(TICKET_RESPONSE.getCpf());
        assertThat(sut.getEvent().getId()).isEqualTo(TICKET_RESPONSE.getEvent().getId());
    }

    @Test
    public void getFullTicket_ByNonExistingId_ReturnsTicketResponse(){
        String ticketId = "2";
        when(ticketRepository.findByIdAndDeletedFalse(ticketId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ticketService.findFullById(ticketId))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessageContaining(String.format("ticket with id=%s not found", ticketId));
    }

    @Test
    public void getAllFullTickets_ByExistingTicketWithCPF_ReturnsTicketResponse(){
        String cpf = "64574764228";
        when(ticketRepository.findByCpfAndDeletedFalse(cpf)).thenReturn(TICKET_LIST);
        when(eventService.findById("eventId")).thenReturn(EVENT);

        List<TicketResponseDto> sut = ticketService.findByCpf(cpf);

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(2);
    }

    @Test
    public void getAllFullTickets_ByNonExistingTicketWithCPF_ReturnsTicketResponse(){
        String cpf = "64574764228";
        when(ticketRepository.findByCpfAndDeletedFalse(cpf)).thenReturn(new ArrayList<>());
        when(eventService.findById("eventId")).thenReturn(EVENT);

        assertThatThrownBy(() -> ticketService.findByCpf(cpf))
                .isInstanceOf(TicketNotFoundException.class)
                .hasMessageContaining(String.format("ticket with cpf=%s not found", cpf));
    }

    @Test
    public void updateTicket_ByExistingId_ReturnsVoid(){
        String ticketId = "1";
        when(ticketRepository.findByIdAndDeletedFalse(ticketId)).thenReturn(Optional.of(TICKET_SAVED));

        assertThatCode(() -> ticketService.update(ticketId, TICKET_UPDATE))
                .doesNotThrowAnyException();
    }

    @Test
    public void updateTicket_ByExistingIdAndNull_ReturnsVoid(){
        String ticketId = "1";
        when(ticketRepository.findByIdAndDeletedFalse(ticketId)).thenReturn(Optional.of(TICKET_SAVED));

        assertThatCode(() -> ticketService.update(ticketId, TICKET_UPDATE_VOID))
                .doesNotThrowAnyException();
    }

    @Test
    public void softDeleteTicket_ByExistingId_ReturnsVoid(){
        String ticketId = "1";
        when(ticketRepository.findByIdAndDeletedFalse(ticketId)).thenReturn(Optional.of(TICKET_SAVED));

        assertThatCode(() -> ticketService.softDelete(ticketId))
                .doesNotThrowAnyException();
    }

    @Test
    public void hasTicket_ByExistingTicket_ReturnsHasTicketResponse(){
        String eventId = "eventId";
        when(ticketRepository.existsTicketsByEventIdAndDeletedFalse(eventId)).thenReturn(true);

        HasTicketsDto sut = ticketService.hasTickets(eventId);

        assertThat(sut).isNotNull();
        assertThat(sut.getEventId()).isEqualTo(eventId);
        assertThat(sut.getHasTickets()).isTrue();
    }

    @Test
    public void hasTicket_ByNonExistingTicket_ReturnsHasTicketResponse(){
        String eventId = "eventId";
        when(ticketRepository.existsTicketsByEventIdAndDeletedFalse(eventId)).thenReturn(false);

        HasTicketsDto sut = ticketService.hasTickets(eventId);

        assertThat(sut).isNotNull();
        assertThat(sut.getEventId()).isEqualTo(eventId);
        assertThat(sut.getHasTickets()).isFalse();
    }
}

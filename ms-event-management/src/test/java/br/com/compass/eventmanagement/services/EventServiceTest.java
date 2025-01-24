package br.com.compass.eventmanagement.services;

import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.exceptions.EventNotFoundException;
import br.com.compass.eventmanagement.exceptions.TicketLinkedException;
import br.com.compass.eventmanagement.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static br.com.compass.eventmanagement.common.AddressConstants.*;
import static br.com.compass.eventmanagement.common.EventConstants.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private AddressService addressService;

    @Mock
    private TicketService ticketService;

    @Mock
    private EventRepository repository;

    @Test
    public void insertEvent_ByValidRequest_ReturnsEvent() {
        when(addressService.findById("01001000")).thenReturn(ADDRESS_SAVED);
        when(repository.insert(VALID_EVENT)).thenReturn(VALID_EVENT_SAVED);

        Event sut = eventService.insert(EVENT_REQUEST);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(VALID_EVENT_SAVED);
    }

    @Test
    public void getEvent_ByExistingId_ReturnsEvent() {
        when(repository.findById("eventId")).thenReturn(Optional.of(VALID_EVENT_SAVED));

        Event sut = eventService.findById("eventId");

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(VALID_EVENT_SAVED);
    }

    @Test
    public void getEvent_ByNonExistingId_ReturnsEvent() {
        String eventId = "eventId";
        when(repository.findById(eventId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.findById(eventId))
                .isInstanceOf(EventNotFoundException.class)
                .hasMessageContaining(String.format("event with id=%s not found", eventId));

    }

    @Test
    public void getAllEvents_Void_ReturnsEventList() {
        when(repository.findAll()).thenReturn(EVENT_LIST);

        List<Event> sut = eventService.findAll();

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(3);
    }

    @Test
    public void getAllEventsSortedAsc_Void_ReturnsEventList() {
        List<Event> eventSorted = EVENT_LIST.stream().sorted(Comparator.comparing(Event::getName)).toList();
        Sort sort = Sort.by("name").ascending();
        when(repository.findAll(sort)).thenReturn(eventSorted);

        List<Event> sut = eventService.findAllSorted("asc");

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(3);
        assertThat(sut.get(0).getName()).isEqualTo("Boiada");
    }

    @Test
    public void getAllEventsSortedDesc_Void_ReturnsEventList() {
        List<Event> eventSorted = EVENT_LIST.stream().sorted(Comparator.comparing(Event::getName).reversed()).toList();
        Sort sort = Sort.by("name").descending();
        when(repository.findAll(sort)).thenReturn(eventSorted);

        List<Event> sut = eventService.findAllSorted("desc");

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(3);
        assertThat(sut.get(0).getName()).isEqualTo("Zumba");
    }

    @Test
    public void updateEvent_NoTickets_Void() {
        String eventId = "eventId";
        when(ticketService.hasTickets(eventId)).thenReturn(false);
        when(addressService.findById("01001000")).thenReturn(ADDRESS_SAVED);
        when(repository.findById(eventId)).thenReturn(Optional.of(VALID_EVENT_SAVED));

        assertThatCode(() -> eventService
                .update(eventId, EVENT_UPDATE_REQUEST))
                .doesNotThrowAnyException();
    }

    @Test
    public void updateEvent_HasTickets_Void() {
        String eventId = "eventId";
        when(ticketService.hasTickets(eventId)).thenReturn(true);
        when(repository.findById(eventId)).thenReturn(Optional.of(VALID_EVENT_SAVED));

        assertThatCode(() -> eventService.update(eventId, EVENT_UPDATE_REQUEST))
                .isInstanceOf(TicketLinkedException.class)
                .hasMessageContaining("error when updating, there are linked tickets");
    }

    @Test
    public void deleteEvent_NoTickets_Void() {
        when(ticketService.hasTickets("eventId")).thenReturn(false);
        when(repository.findById("eventId")).thenReturn(Optional.of(VALID_EVENT_SAVED));

        assertThatCode(() -> eventService.delete("eventId")).doesNotThrowAnyException();
    }

    @Test
    public void deleteEvent_HasTickets_Void() {
        String eventId = "eventId";
        when(ticketService.hasTickets(eventId)).thenReturn(true);
        when(repository.findById(eventId)).thenReturn(Optional.of(VALID_EVENT_SAVED));

        assertThatCode(() -> eventService.delete(eventId))
                .isInstanceOf(TicketLinkedException.class)
                .hasMessageContaining("error when deleting, there are linked tickets");
    }
}

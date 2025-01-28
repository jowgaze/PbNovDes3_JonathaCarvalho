package br.com.compass.eventmanagement.controllers;

import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.dtos.event.EventRequestDto;
import br.com.compass.eventmanagement.dtos.event.EventUpdateRequestDto;
import br.com.compass.eventmanagement.exceptions.FeignNotFoundException;
import br.com.compass.eventmanagement.exceptions.FeignRequestException;
import br.com.compass.eventmanagement.exceptions.TicketLinkedException;
import br.com.compass.eventmanagement.exceptions.TicketServiceException;
import br.com.compass.eventmanagement.services.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Comparator;
import java.util.List;

import static br.com.compass.eventmanagement.common.EventConstants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@WebMvcTest(EventController.class)
public class EventControllerTest {
    private final String URL = "/br/com/compass/eventmanagement/v1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService service;

    @Test
    public void createEvent_WithValidData_ReturnsEventDto() throws Exception {
        String body = objectMapper.writeValueAsString(EVENT_REQUEST);
        when(service.insert(Mockito.any(EventRequestDto.class))).thenReturn(VALID_EVENT_SAVED);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/create-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    public void createEvent_WithInvalidData_Returns422() throws Exception {
        String body = objectMapper.writeValueAsString(EVENT_REQUEST_INVALID);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/create-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(422));
    }

    @Test
    public void createEvent_WithInvalidData_Returns404() throws Exception {
        String body = objectMapper.writeValueAsString(EVENT_REQUEST);
        when(service.insert(Mockito.any(EventRequestDto.class))).thenThrow(FeignNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/create-event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(404));
    }

    @Test
    public void getEvent_ByExistingId_ReturnsEventDto() throws Exception {
        when(service.findById("eventId")).thenReturn(VALID_EVENT_SAVED);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/get-event/eventId"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllEvents_Void_ReturnsListEventDto() throws Exception {
        when(service.findAll()).thenReturn(EVENT_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/get-all-events"))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllSortedEvents_Void_ReturnsListEventDto() throws Exception {
        List<Event> eventSorted = EVENT_LIST.stream().sorted(Comparator.comparing(Event::getName)).toList();
        when(service.findAllSorted("asc")).thenReturn(eventSorted);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/get-all-events/sorted"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEvent_WithValidData_Returns204() throws Exception {
        String body = objectMapper.writeValueAsString(EVENT_UPDATE_REQUEST);
        doNothing().when(service).update(Mockito.any(String.class), Mockito.any(EventUpdateRequestDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/update-event/eventId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateEvent_WithInvalidData_Returns422() throws Exception {
        String body = objectMapper.writeValueAsString(EVENT_UPDATE_REQUEST_INVALID);
        doThrow(FeignRequestException.class).when(service).update(Mockito.any(String.class), Mockito.any(EventUpdateRequestDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/update-event/eventId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(422));
    }

    @Test
    public void deleteEvent_WithValidData_Returns204() throws Exception {
        doNothing().when(service).delete(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/delete-event/eventId"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteEvent_WithTicketLinked_Returns409() throws Exception {
        doThrow(TicketLinkedException.class).when(service).delete(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/delete-event/eventId"))
                .andExpect(status().is(409));
    }

    @Test
    public void deleteEvent_WithTicketUnavailable_Returns409() throws Exception {
        doThrow(TicketServiceException.class).when(service).delete(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/delete-event/eventId"))
                .andExpect(status().is(503));
    }
}

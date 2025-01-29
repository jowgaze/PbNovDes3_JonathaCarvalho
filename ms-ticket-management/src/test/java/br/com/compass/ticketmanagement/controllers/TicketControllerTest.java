package br.com.compass.ticketmanagement.controllers;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import br.com.compass.ticketmanagement.dtos.event.HasTicketsDto;
import br.com.compass.ticketmanagement.dtos.ticket.TicketUpdateRequestDto;
import br.com.compass.ticketmanagement.exceptions.FeignNotFoundException;
import br.com.compass.ticketmanagement.exceptions.FeignRequestException;
import br.com.compass.ticketmanagement.exceptions.TicketNotFoundException;
import br.com.compass.ticketmanagement.services.TicketService;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static br.com.compass.ticketmanagement.common.TicketConstants.*;


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@WebMvcTest(TicketController.class)
public class TicketControllerTest {
    private final String URL = "/br/com/compass/ticketmanagement/v1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService service;

    @Test
    public void createTicket_WithValidData_ReturnsTicketDto() throws Exception {
        String body = objectMapper.writeValueAsString(TICKET_REQUEST);
        when(service.insert(Mockito.any(Ticket.class))).thenReturn(TICKET_RESPONSE);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/create-ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    public void createTicket_WithEventNotFound_Returns404() throws Exception {
        String body = objectMapper.writeValueAsString(TICKET_REQUEST);
        when(service.insert(Mockito.any(Ticket.class))).thenThrow(FeignNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/create-ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createTicket_WithEventNotFound_Returns503() throws Exception {
        String body = objectMapper.writeValueAsString(TICKET_REQUEST);
        when(service.insert(Mockito.any(Ticket.class))).thenThrow(FeignRequestException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/create-ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(503));
    }

    @Test
    public void createTicket_WithInvalidData_Returns422() throws Exception {
        String body = objectMapper.writeValueAsString(TICKET_REQUEST_INVALID);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL + "/create-ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(422));
    }

    @Test
    public void getTicket_ByExistingId_ReturnsTicketDto() throws Exception {
        when(service.findFullById("1")).thenReturn(TICKET_RESPONSE);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/get-ticket/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTicket_ByNonExistingId_Returns404() throws Exception {
        when(service.findFullById("1")).thenThrow(TicketNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/get-ticket/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTicket_ByValidCpf_ReturnsListTicketsDto() throws Exception {
        when(service.findByCpf(Mockito.any(String.class))).thenReturn(TICKET_RESPONSE_LIST);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/get-ticket-by-cpf/64574764228"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTicket_ByInvalidCpf_Returns422() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/get-ticket-by-cpf/00000000000"))
                .andExpect(status().is(422));
    }

    @Test
    public void updateTicket_WithValidData_Returns204() throws Exception {
        String body = objectMapper.writeValueAsString(TICKET_UPDATE);
        doNothing().when(service).update(Mockito.any(String.class), Mockito.any(TicketUpdateRequestDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/update-ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNoContent());
    }

    @Test
    public void cancelTicket_WithValidData_Returns204() throws Exception {
        doNothing().when(service).softDelete(Mockito.any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/cancel-ticket/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void checkEvent_WithValidData_Returns200() throws Exception {
        when(service.hasTickets(Mockito.any(String.class))).thenReturn(new HasTicketsDto("eventId", false));

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/check-tickets-by-event/eventId"))
                .andExpect(status().isOk());
    }
}

package br.com.compass.ticketmanagement.common;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import br.com.compass.ticketmanagement.dtos.ticket.TicketRequestDto;
import br.com.compass.ticketmanagement.dtos.ticket.TicketResponseDto;
import br.com.compass.ticketmanagement.dtos.ticket.TicketUpdateRequestDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.compass.ticketmanagement.common.EventConstants.*;

public class TicketConstants {
    public static final Ticket TICKET_SAVED = new Ticket(
            "1",
            "64574764228",
            "José",
            "jose@mail.com",
            "eventId",
            "R$ 50,00",
            "U$ 10,00",
            false
    );

    public static Ticket TICKET = new Ticket(
            null,
            "64574764228",
            "José",
            "jose@mail.com",
            "eventId",
            "R$ 50,00",
            "U$ 10,00",
            false
    );

    public static final TicketRequestDto TICKET_REQUEST = new TicketRequestDto(
            "64574764228",
            "José",
            "jose@mail.com",
            "eventId",
            "R$ 50,00",
            "U$ 10,00"
    );

    public static final TicketResponseDto TICKET_RESPONSE = new TicketResponseDto(
            "1",
            "64574764228",
            "José",
            "jose@mail.com",
            EVENT,
            "R$ 50,00",
            "U$ 10,00"
    );

    public static final TicketUpdateRequestDto TICKET_UPDATE = new TicketUpdateRequestDto(
            "64574764228",
            "José Maria",
            "jose@mail.com",
            "R$ 50,00",
            "U$ 10,00"
    );

    public static final TicketUpdateRequestDto TICKET_UPDATE_VOID = new TicketUpdateRequestDto(
            null, null, null, null, null
    );

    public static final List<Ticket> TICKET_LIST = new ArrayList<>(Arrays.asList(
            TICKET_SAVED,
            new Ticket(
                    "2",
                    "64574764228",
                    "José",
                    "jose@mail.com",
                    "eventId",
                    "R$ 50,00",
                    "U$ 10,00",
                    false
            )
    ));
}
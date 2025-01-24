package br.com.compass.ticketmanagement.common;

import br.com.compass.ticketmanagement.dtos.event.AddressResponseDto;
import br.com.compass.ticketmanagement.dtos.event.EventResponseDto;

import java.time.LocalDateTime;

public class EventConstants {
    public static final AddressResponseDto ADDRESS = new AddressResponseDto("01001-000", "Praça da Sé", "Sé", "São Paulo", "SP");
    public static final EventResponseDto EVENT = new EventResponseDto("eventId","Show", LocalDateTime.parse("2025-11-13T20:00:00"), ADDRESS);
}

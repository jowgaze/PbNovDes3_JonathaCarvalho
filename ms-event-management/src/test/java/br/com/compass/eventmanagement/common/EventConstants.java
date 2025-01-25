package br.com.compass.eventmanagement.common;

import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.dtos.event.EventRequestDto;
import br.com.compass.eventmanagement.dtos.event.EventUpdateRequestDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.compass.eventmanagement.common.AddressConstants.ADDRESS_SAVED;

public class EventConstants {
    public static final Event VALID_EVENT = new Event("Show", LocalDateTime.parse("2025-11-13T20:00:00"), ADDRESS_SAVED);
    public static final Event VALID_EVENT_SAVED = new Event("eventId","Show", LocalDateTime.parse("2025-11-13T20:00:00"), ADDRESS_SAVED);
    public static final EventRequestDto EVENT_REQUEST = new EventRequestDto("Show", LocalDateTime.parse("2025-11-13T20:00:00"), "01001000");
    public static final EventUpdateRequestDto EVENT_UPDATE_REQUEST = new EventUpdateRequestDto("Show de Rock", LocalDateTime.parse("2025-12-13T20:00:00"), "01001000");
    public static final List<Event> EVENT_LIST = new ArrayList<>(
            Arrays.asList(
                    new Event("eventId1", "Show de Rock", LocalDateTime.parse("2025-11-13T20:00:00"), ADDRESS_SAVED),
                    new Event("eventId2", "Zumba", LocalDateTime.parse("2025-11-14T20:00:00"), ADDRESS_SAVED),
                    new Event("eventId3", "Boiada", LocalDateTime.parse("2025-11-15T20:00:00"), ADDRESS_SAVED)
            )
    );
}

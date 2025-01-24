package br.com.compass.ticketmanagement.services;

import br.com.compass.ticketmanagement.dtos.event.EventResponseDto;
import br.com.compass.ticketmanagement.exceptions.FeignNotFoundException;
import br.com.compass.ticketmanagement.exceptions.FeignRequestException;
import br.com.compass.ticketmanagement.services.client.EventClient;
import feign.FeignException;
import feign.RetryableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static br.com.compass.ticketmanagement.common.EventConstants.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(SpringExtension.class)
public class EventServiceTest {
    @InjectMocks
    private EventService service;

    @Mock
    private EventClient client;

    @Test
    public void getEvent_ByExistingId_ReturnsResponseDto() {
        String eventId = "eventId";
        when(client.findById(eventId)).thenReturn(EVENT);

        EventResponseDto sut = service.findById(eventId);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(EVENT);
    }

    @Test
    public void getEvent_ByNonExistingId_Returns404() {
        String eventId = "eventId";
        when(client.findById(eventId)).thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> service.findById(eventId))
                .isInstanceOf(FeignNotFoundException.class)
                .hasMessageContaining(String.format("event with id=%s not found", eventId));
    }

    @Test
    public void getEvent_ByNonExistingId_Returns503() {
        String eventId = "eventId";
        when(client.findById(eventId)).thenThrow(RetryableException.class);

        assertThatThrownBy(() -> service.findById(eventId))
                .isInstanceOf(FeignRequestException.class)
                .hasMessageContaining("event service unavailable");
    }

}

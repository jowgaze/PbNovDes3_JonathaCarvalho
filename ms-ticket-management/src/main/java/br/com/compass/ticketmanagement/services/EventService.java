package br.com.compass.ticketmanagement.services;

import br.com.compass.ticketmanagement.exceptions.FeignNotFoundException;
import br.com.compass.ticketmanagement.exceptions.FeignRequestException;
import br.com.compass.ticketmanagement.services.client.EventClient;
import br.com.compass.ticketmanagement.dtos.event.EventResponseDto;
import feign.FeignException;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventClient eventClient;

    public EventResponseDto findById(String id){
        try {
            return eventClient.findById(id);
        } catch (FeignException.NotFound e){
            log.error("Event not found. id: {}", id);
            throw new FeignNotFoundException(String.format("event with id=%s not found", id));
        } catch (RetryableException e){
            log.error("Event service unavailable");
            throw new FeignRequestException("event service unavailable");
        }
    }
}

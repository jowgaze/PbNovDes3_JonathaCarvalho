package br.com.compass.eventmanagement.services;

import br.com.compass.eventmanagement.domain.address.Address;
import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.domain.event.dtos.EventRequestDto;
import br.com.compass.eventmanagement.domain.event.mapper.EventMapper;
import br.com.compass.eventmanagement.exceptions.EventNotFoundException;
import br.com.compass.eventmanagement.repositories.EventRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRespository eventRespository;
    private final AddressService addressService;

    @Transactional
    public Event insert(EventRequestDto body) {
        String zipCode = body.getZipCode();
        Address address = addressService.findById(zipCode);
        Event event = EventMapper.toEntity(body, address);
        return eventRespository.insert(event);
    }

    @Transactional(readOnly = true)
    public Event findById(String id) {
        return eventRespository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event not found. id: {}", id);
                    return new EventNotFoundException("event not found");
                });
    }

    @Transactional(readOnly = true)
    public List<Event> findAll(){
        return eventRespository.findAll();
    }
}

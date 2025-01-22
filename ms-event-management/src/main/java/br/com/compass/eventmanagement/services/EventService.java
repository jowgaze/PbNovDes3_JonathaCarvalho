package br.com.compass.eventmanagement.services;

import br.com.compass.eventmanagement.domain.address.Address;
import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.domain.event.dtos.EventRequestDto;
import br.com.compass.eventmanagement.domain.event.mapper.EventMapper;
import br.com.compass.eventmanagement.repositories.EventRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRespository eventRespository;
    private final AddressService addressService;

    @Transactional
    public Event insert(EventRequestDto body){
        String zipCode = body.getZipCode();
        Address address = addressService.findById(zipCode);

        Event event = EventMapper.toEntity(body, address);
        return eventRespository.insert(event);
    }
}

package br.com.compass.eventmanagement.services;

import br.com.compass.eventmanagement.domain.address.Address;
import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.dtos.event.EventRequestDto;
import br.com.compass.eventmanagement.dtos.event.EventUpdateRequestDto;
import br.com.compass.eventmanagement.dtos.event.mapper.EventMapper;
import br.com.compass.eventmanagement.exceptions.EventNotFoundException;
import br.com.compass.eventmanagement.exceptions.TicketLinkedException;
import br.com.compass.eventmanagement.repositories.EventRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRespository eventRespository;
    private final AddressService addressService;
    private final TicketService ticketService;

    @Transactional
    public Event insert(EventRequestDto request) {
        String zipCode = request.getZipCode();
        Address address = addressService.findById(zipCode);
        Event event = EventMapper.toEntity(request, address);
        return eventRespository.insert(event);
    }

    @Transactional(readOnly = true)
    public Event findById(String id) {
        return eventRespository.findById(id)
                .orElseThrow(() -> {
                    log.error("Event not found. id: {}", id);
                    return new EventNotFoundException(String.format("event with id=%s not found", id));
                });
    }

    @Transactional(readOnly = true)
    public List<Event> findAll() {
        return eventRespository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Event> findAllSorted(String direction) {
        Sort sort = Sort.by("name");
        sort = direction.equals("desc") ? sort.descending() : sort.ascending();
        return eventRespository.findAll(sort);
    }

    @Transactional
    public void update(String id, EventUpdateRequestDto request) {
        Event event = findById(id);
        if (ticketService.hasTickets(id)) {
            log.error("Error when updating, there are linked tickets. id: {}", id);
            throw new TicketLinkedException("error when updating, there are linked tickets");
        }

        if (request.getName() != null)
            event.setName(request.getName());

        if (request.getDateTime() != null)
            event.setDate(request.getDateTime());

        if (request.getZipCode() != null) {
            Address address = addressService.findById(request.getZipCode());
            event.setAddress(address);
        }

        eventRespository.save(event);
    }

    @Transactional
    public void delete(String id) {
        Event event = findById(id);
        if (ticketService.hasTickets(id)) {
            log.error("Error when deleting, there are linked tickets. id: {}", id);
            throw new TicketLinkedException("error when deleting, there are linked tickets");
        }

        eventRespository.delete(event);
    }
}

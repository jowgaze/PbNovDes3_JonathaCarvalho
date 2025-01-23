package br.com.compass.ticketmanagement.services;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import br.com.compass.ticketmanagement.domain.ticket.dtos.TicketResponseDto;
import br.com.compass.ticketmanagement.domain.ticket.dtos.TicketUpdateRequestDto;
import br.com.compass.ticketmanagement.domain.ticket.mapper.TicketMapper;
import br.com.compass.ticketmanagement.exceptions.TicketNotFoundException;
import br.com.compass.ticketmanagement.repositories.TicketRepository;
import br.com.compass.ticketmanagement.services.client.dtos.EventResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventService eventService;

    @Transactional
    public TicketResponseDto insert(Ticket ticket) {
        ticket.setId(generateId());
        TicketResponseDto response = getTicketFull(ticket);
        ticketRepository.save(ticket);
        return response;
    }

    @Transactional(readOnly = true)
    public TicketResponseDto findFullById(String id) {
        Ticket ticket = findById(id);
        return getTicketFull(ticket);
    }

    @Transactional(readOnly = true)
    public List<TicketResponseDto> findByCpf(String cpf) {
        List<Ticket> ticketList = ticketRepository.findByCpf(cpf);

        if (ticketList.isEmpty()) {
            log.error("ticket not found. cpf: {}", cpf);
            throw new TicketNotFoundException(String.format("ticket with cpf=%s not found", cpf));
        }

        return ticketList.stream().map(this::getTicketFull).toList();
    }

    @Transactional
    public void update(String id, TicketUpdateRequestDto request) {
        Ticket ticket = findById(id);

        if (request.getCpf() != null)
            ticket.setCpf(request.getCpf());

        if (request.getCustomerName() != null)
            ticket.setCustomerName(request.getCustomerName());

        if (request.getCustomerMail() != null)
            ticket.setCustomerMail(request.getCustomerMail());

        if (request.getBrlAmount() != null)
            ticket.setBrlAmount(request.getBrlAmount());

        if (request.getUsdAmount() != null)
            ticket.setUsdAmount(request.getUsdAmount());

        ticketRepository.save(ticket);
    }


    private String generateId() {
        return Long.toString(ticketRepository.count() + 1);
    }

    private Ticket findById(String id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("ticket not found. id: {}", id);
                    return new TicketNotFoundException(String.format("ticket with id=%s not found", id));
                });
    }

    private TicketResponseDto getTicketFull(Ticket ticket) {
        String eventId = ticket.getEventId();
        EventResponseDto event = eventService.findById(eventId);
        return TicketMapper.toDto(ticket, event);
    }
}

package br.com.compass.ticketmanagement.domain.ticket.mapper;

import br.com.compass.ticketmanagement.domain.ticket.Ticket;
import br.com.compass.ticketmanagement.domain.ticket.dtos.TicketRequestDto;
import br.com.compass.ticketmanagement.domain.ticket.dtos.TicketResponseDto;
import br.com.compass.ticketmanagement.services.client.dtos.EventResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class TicketMapper {
    public static Ticket toEntity(TicketRequestDto dto){
        return new ModelMapper().map(dto, Ticket.class);
    }

    public static TicketResponseDto toDto(Ticket ticket, EventResponseDto event){
        PropertyMap<Ticket, TicketResponseDto> props = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setEvent(event);
            }
        };
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(props);
        return modelMapper.map(ticket, TicketResponseDto.class);
    }
}

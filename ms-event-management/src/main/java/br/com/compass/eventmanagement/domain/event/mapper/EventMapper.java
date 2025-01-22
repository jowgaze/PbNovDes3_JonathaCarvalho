package br.com.compass.eventmanagement.domain.event.mapper;

import br.com.compass.eventmanagement.domain.address.Address;
import br.com.compass.eventmanagement.domain.event.Event;
import br.com.compass.eventmanagement.domain.event.dtos.EventRequestDto;
import br.com.compass.eventmanagement.domain.event.dtos.EventResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;

public class EventMapper {

    public static Event toEntity(EventRequestDto dto, Address address){
        PropertyMap<EventRequestDto, Event> props = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setAddress(address);
            }
        };

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(props);
        return modelMapper.map(dto, Event.class);
    }

    public static EventResponseDto toDto(Event entity){
        return new ModelMapper().map(entity, EventResponseDto.class);
    }

    public static List<EventResponseDto> toListDto(List<Event> eventList){
        return eventList.stream().map(EventMapper::toDto).toList();
    }
}

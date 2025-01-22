package br.com.compass.eventmanagement.domain.event.dtos;

import br.com.compass.eventmanagement.domain.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventResponseDto {
    private String id;
    private String name;
    private LocalDateTime dateTime;
    private Address address;
}

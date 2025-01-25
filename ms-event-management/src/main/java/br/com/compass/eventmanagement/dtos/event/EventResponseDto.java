package br.com.compass.eventmanagement.dtos.event;

import br.com.compass.eventmanagement.domain.address.Address;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventResponseDto {
    private String id;
    private String name;
    private LocalDateTime dateTime;
    private Address address;
}

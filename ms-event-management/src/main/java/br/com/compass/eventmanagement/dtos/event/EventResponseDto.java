package br.com.compass.eventmanagement.dtos.event;

import br.com.compass.eventmanagement.domain.address.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateTime;
    private Address address;
}

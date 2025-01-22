package br.com.compass.eventmanagement.domain.event.dtos;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventUpdateRequestDto {
    private String name;
    private LocalDateTime dateTime;

    @Length(min = 8, max = 8)
    private String zipCode;
}

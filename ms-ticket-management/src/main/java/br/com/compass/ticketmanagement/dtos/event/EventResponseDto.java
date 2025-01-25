package br.com.compass.ticketmanagement.dtos.event;

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
    private AddressResponseDto address;
}
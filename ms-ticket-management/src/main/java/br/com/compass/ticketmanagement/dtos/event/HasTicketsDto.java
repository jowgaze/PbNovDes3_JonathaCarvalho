package br.com.compass.ticketmanagement.dtos.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HasTicketsDto {
    private String eventId;
    private Boolean hasTickets;
}

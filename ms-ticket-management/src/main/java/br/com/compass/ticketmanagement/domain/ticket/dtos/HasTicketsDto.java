package br.com.compass.ticketmanagement.domain.ticket.dtos;

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

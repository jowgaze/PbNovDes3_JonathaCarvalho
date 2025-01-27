package br.com.compass.eventmanagement.dtos.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HasTicketsDto {
    private String eventId;
    private Boolean hasTickets;
    private String error;
}
